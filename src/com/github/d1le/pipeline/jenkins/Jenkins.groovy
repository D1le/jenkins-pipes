package com.github.d1le.pipeline.jenkins

import com.github.d1le.pipeline.jenkins.api.JenkinsApi

/**
 * @author Alexey Lapin
 */
interface Jenkins<T extends Credentials> {

    JenkinsApi api()

    BuildResult newBuild(BuildDef buildDef)

    T credentials()
}
