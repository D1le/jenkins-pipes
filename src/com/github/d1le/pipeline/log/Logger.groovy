package com.github.d1le.pipeline.log

/**
 * @author Alexey Lapin
 */
interface Logger {

    void debug(Object message)

    void info(Object message)

    void warn(Object message)

    void error(Object message)

    void log(Object message)
}
