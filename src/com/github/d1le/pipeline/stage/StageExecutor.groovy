package com.github.d1le.pipeline.stage

import jenkins.model.InterruptedBuildAction
import com.github.d1le.pipeline.log.Logger
import com.github.d1le.pipeline.log.LoggerManager

import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

/**
 * A wrapper for {@code stage} step with tracking.
 * {@code StageExecutor} remembers all stages it runs along with their statuses.
 * @author Alexey Lapin
 */
class StageExecutor implements Serializable {

    public static final String STATUS_ABORTED = "ABORTED"
    public static final String STATUS_SUCCESS = "SUCCESS"
    public static final String STATUS_SKIPPED = "SKIPPED"
    public static final String STATUS_UNSTABLE = "UNSTABLE"
    public static final String STATUS_FAILED = "FAILED"
    public static final String STATUS_FAILURE = "FAILURE"
    public static final List<String> STATUS_ALL = [STATUS_SUCCESS, STATUS_SKIPPED, STATUS_UNSTABLE, STATUS_FAILED, STATUS_FAILURE, STATUS_ABORTED]

    Logger logger

    List<StageStatus> results = []

    /**
     * Initialize logger.
     * This method forced by cps paradigm
     * @return this instance
     */
    StageExecutor loggable() {
        logger = LoggerManager.getLogger("StageExecutor")
        this
    }

    /**
     * Executes stage closure and remembers result.
     * If closure returns explicit status string (eg. SUCCESS, FAILED...), this status is remembered instead.
     * @param name of stage
     * @param condition - whether execute stage or skip
     * @param closure
     * @return anything from closure
     */
    def run(String name, boolean condition = true, Closure closure) {
        run(new StageDef().name(name).condition(condition).closure(closure))
    }

    /**
     * Executes stage closure and remembers result.
     * If closure returns explicit status string (eg. SUCCESS, FAILED...), this status is remembered instead.
     * @param stageDef
     * @return anything from closure
     */
    def run(StageDef stageDef) {
        def result = null
        def status = null
        try {
            logger?.debug("Stage '${stageDef.name}' started")
            ctx().getExec().stage(stageDef.name, {
                if (stageDef.condition) {
                    result = stageDef.closure.call()
                    if (STATUS_ALL.contains(result?.toString())) {
                        status = StageStatus.of(stageDef, result.toString())
                    } else {
                        status = StageStatus.of(stageDef, STATUS_SUCCESS)
                    }
                } else {
                    status = StageStatus.of(stageDef, STATUS_SKIPPED)
                }
            })
            logger?.debug("Stage '${stageDef?.name}' finished with status ${status?.result}")
            return result
        } catch (Throwable t) {
            if (ctx().getScript().currentBuild.getRawBuild().getAction(InterruptedBuildAction.class)) {
                status = StageStatus.of(stageDef, STATUS_ABORTED)
            } else {
                status = StageStatus.of(stageDef, STATUS_FAILED)
            }
            logger?.error("Stage '${stageDef?.name}' finished with status ${status?.result}: ${t.getMessage()}")
            throw t
        } finally {
            results.add(status)
        }
    }
}
