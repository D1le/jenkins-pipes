package ru.sbrf.pegi18.pipeline.jenkins

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import hudson.AbortException
import hudson.model.Result
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import ru.sbrf.pegi18.pipeline.jenkins.api.JenkinsApi

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class LocalJenkins<T extends Credentials> extends AbstractJenkins<T> {

    @Override
    JenkinsApi api() {
        return null
    }

    @Override
    BuildResult newBuild(BuildDef buildDef) {
        RunWrapper build = null
        try {
            build = (RunWrapper) exec.build(job: buildDef.name, wait: buildDef.waitForCompletion, propagate: false, parameters: buildDef.parameterList())
            if (build != null && buildDef.propagateErrors) {
                if (build.getResult() in errorPropagationStatuses || buildDef.propagateUnstableAsFailure && build.getResult() == Result.UNSTABLE.toString()) {
                    throw new AbortException("${build.getFullDisplayName()} completed with status ${build.getResult()} (propagate: false to ignore)")
                }
            }
        } catch (Throwable t) {
            if (buildDef.propagateErrors) {
                throw t
            }
        }
        new RunWrapperBasedBuildResult(impl: build)
    }
}
