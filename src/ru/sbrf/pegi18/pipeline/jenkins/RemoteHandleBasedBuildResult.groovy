package ru.sbrf.pegi18.pipeline.jenkins

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
