package ru.sbrf.pegi18.pipeline.jenkins

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import hudson.model.Result

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
abstract class AbstractJenkins<T extends Credentials> implements Jenkins<T> {

    public static final List<String> errorPropagationStatuses = [Result.FAILURE.toString(), Result.ABORTED.toString()]

    Executable exec

    String url
    String baseFolder
    T credentials

    @Override
    T credentials() {
        credentials
    }
}
