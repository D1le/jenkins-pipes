package ru.sbrf.pegi18.pipeline.stage

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import static ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '', includes = 'stageExecutor')
class ParallelStageExecution {

    StageExecutor stageExecutor
    private List<StageDef> stageDefs = []

    static ParallelStageExecution stagedParallel() {
        new ParallelStageExecution()
    }

    ParallelStageExecution stage(String name, boolean condition = true, Closure closure) {
        stageDefs.add(new StageDef().name(name).condition(condition).closure(closure))
        this
    }

    ParallelStageExecution stage(StageDef stageDef) {
        stageDefs.add(stageDef)
        this
    }

    def waitStages() {
        def executor = stageExecutor
        if (!executor) {
            executor = ctx().getStageExecutor()
        }

        Map stages = stageDefs.inject([:]) { map, stageDef -> map << [(stageDef.name): { executor.run(stageDef) }] }
        ctx().getExec().parallel(stages)
    }

}
