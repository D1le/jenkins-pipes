package com.github.d1le.pipeline.log

import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

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
