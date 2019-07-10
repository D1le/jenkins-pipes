package com.github.d1le.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
class PipelineSupport implements Serializable {

    private static BuildContext buildContext

    static <T extends BuildContext> void setContext(T buildContext) {
        PipelineSupport.buildContext = buildContext
    }

    static <T extends BuildContext> void pipe(T buildContext, Closure closure) {
        setContext(buildContext)
        buildContext.getScript().timestamps {
            buildContext.getScript().ansiColor('xterm') {
                closure()
            }
        }
    }

    static <T extends BuildContext> T ctx() {
        (T) buildContext
    }
}
