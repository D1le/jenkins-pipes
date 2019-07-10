package ru.sbrf.pegi18.pipeline.jenkins

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class BuildDef {

    public static final String PARAM_BOOLEAN = "BooleanParameterValue"
    public static final String PARAM_STRING = "StringParameterValue"

    String name
    boolean waitForCompletion = true
    boolean propagateErrors = true
    boolean propagateUnstableAsFailure = false
    Map parameters = [:]

    List parameterList() {
        def list = []
        parameters.each { key, value ->
            list.add(value)
        }
        list
    }

    BuildDef parameter(String key, value, String className = null) {
        if (value instanceof Map) {
            parameters.put(key, value)
        } else {
            if (className == null) {
                if (value instanceof Boolean) {
                    className = PARAM_BOOLEAN
                } else {
                    className = PARAM_STRING
                }
            }
            parameters.put(key, [name: key, value: value, $class: className])
        }
        this
    }
}
