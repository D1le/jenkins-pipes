package com.github.d1le.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
class RemoteHandleBasedBuildResult implements BuildResult, Serializable {

    def impl

    @Override
    Object impl() {
        impl
    }

    @Override
    String status() {
        impl.getBuildResult().toString()
    }
}
