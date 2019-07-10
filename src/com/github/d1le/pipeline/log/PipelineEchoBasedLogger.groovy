package com.github.d1le.pipeline.log

import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
class PipelineEchoBasedLogger implements Logger {

    public static final String LEVEL_DEBUG = "DEBUG"
    public static final String LEVEL_INFO = "INFO"
    public static final String LEVEL_WARN = "WARN"
    public static final String LEVEL_ERROR = "ERROR"

    String name

    @Override
    void debug(Object message) {
        ctx().getScript().echo(blue(named(leveled(message, LEVEL_DEBUG))))
    }

    @Override
    void info(Object message) {
        ctx().getScript().echo(green(named(leveled(message, LEVEL_INFO))))
    }

    @Override
    void warn(Object message) {
        ctx().getScript().echo(yellow(named(leveled(message, LEVEL_WARN))))
    }

    @Override
    void error(Object message) {
        ctx().getScript().echo(red(named(leveled(message, LEVEL_ERROR))))
    }

    @Override
    void log(Object message) {
        ctx().getScript().echo("${message}")
    }

    private String named(String message) {
        if(name == null) {
            message
        } else {
            "[${name}]${message}"
        }
    }

    private static String leveled(Object message, String level) {
        "[${level}]${message}"
    }

    private static String green(Object message) {
        colored(message, "\u001B[32m")
    }

    private static String yellow(Object message) {
        colored(message, "\u001B[33m")
    }

    private static String red(Object message) {
        colored(message, "\u001B[31m")
    }

    private static String blue(Object message) {
        colored(message, "\u001B[34m")
    }

    private static String colored(Object message, String color) {
        "${color}${message}\u001B[0m"
    }
}
