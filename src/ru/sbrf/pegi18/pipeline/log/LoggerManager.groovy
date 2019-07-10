package ru.sbrf.pegi18.pipeline.log

import static ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
class LoggerManager {

    private static final String CLASS_WORKFLOWSCRIPT = "WorkflowScript"

    static Logger getLogger(String name) {
        if (CLASS_WORKFLOWSCRIPT.equals(ctx().getScript().getClass().getName())) {
            new PipelineEchoBasedLogger(name: name)
        } else {
            new Log4j2BasedLogger(name)
        }
    }
}
