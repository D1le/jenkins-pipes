package ru.sbrf.pegi18.pipeline.jenkins

import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

/**
 * @author Alexey Lapin
 */
class RunWrapperBasedBuildResult implements BuildResult<RunWrapper> {

    RunWrapper impl

    @Override
    RunWrapper impl() {
        impl
    }

    @Override
    String status() {
        impl.getResult()
    }
}
