package ru.sbrf.pegi18.pipeline.jenkins

import ru.sbrf.pegi18.pipeline.jenkins.api.JenkinsApi

/**
 * @author Alexey Lapin
 */
interface Jenkins<T extends Credentials> {

    JenkinsApi api()

    BuildResult newBuild(BuildDef buildDef)

    T credentials()
}
