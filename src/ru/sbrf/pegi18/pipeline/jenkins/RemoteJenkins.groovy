package ru.sbrf.pegi18.pipeline.jenkins

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import hudson.AbortException
import hudson.model.Result
import ru.sbrf.pegi18.pipeline.jenkins.api.JenkinsApi

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class RemoteJenkins<T extends Credentials> extends AbstractJenkins<T> {

    @Override
    JenkinsApi api() {
        return null
    }

    @Override
    BuildResult newBuild(BuildDef buildDef) {
        def build = null
        try {
            build = exec.triggerRemoteJob([
                abortTriggeredJob      : false,
                blockBuildUntilComplete: buildDef.waitForCompletion,
                auth                   : exec.CredentialsAuth(credentials: credentials.jenkins().id),
                job                    : "${url}${baseFolder}${jobPathByBuildDef(buildDef)}",
                maxConn                : 1,
                parameters             : parametersFromBuildDef(buildDef),
                shouldNotFailBuild     : buildDef.propagateErrors,
                useCrumbCache          : true,
                useJobInfoCache        : true])
            if (build != null && buildDef.propagateErrors) {
                if (build.getBuildStatus() in errorPropagationStatuses || buildDef.propagateUnstableAsFailure && build.getBuildStatus() == Result.UNSTABLE.toString()) {
                    throw new AbortException("${build.getJobFullDisplayName()} completed with status ${build.getBuildStatus()} (propagate: false to ignore)")
                }
            }
        } catch (Throwable t) {
            if (buildDef.propagateErrors) {
                throw t
            }
        }
        new RemoteHandleBasedBuildResult(impl: build)
    }

    private String jobPathByBuildDef(BuildDef buildDef) {
        buildDef.name.split("/").collect({ "job/${it}" }).join("/")
    }

    private String parametersFromBuildDef(BuildDef buildDef) {
        buildDef.parameters.collect({ entry -> "$entry.value.name=$entry.value.value" }).join("\n")
    }
}
