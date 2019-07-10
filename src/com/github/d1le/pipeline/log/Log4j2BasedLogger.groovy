package com.github.d1le.pipeline.log

import org.apache.logging.log4j.LogManager

/**
 * @author Alexey Lapin
 */
class Log4j2BasedLogger implements Logger {

    private org.apache.logging.log4j.Logger log

    Log4j2BasedLogger(def name) {
        log = LogManager.getLogger(name)
    }

    @Override
    void debug(Object message) {
        log.debug(message)
    }

    @Override
    void info(Object message) {
        log.info(message)
    }

    @Override
    void warn(Object message) {
        log.warn(message)
    }

    @Override
    void error(Object message) {
        log.error(message)
    }

    @Override
    void log(Object message) {
        log.info(message)
    }
}
