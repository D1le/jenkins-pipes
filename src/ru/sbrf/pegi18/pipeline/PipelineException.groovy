package ru.sbrf.pegi18.pipeline

/**
 * @author Alexey Lapin
 */
class PipelineException extends RuntimeException {

    PipelineException() {
    }

    PipelineException(String var1) {
        super(var1)
    }

    PipelineException(String var1, Throwable var2) {
        super(var1, var2)
    }

    PipelineException(Throwable var1) {
        super(var1)
    }

    PipelineException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
