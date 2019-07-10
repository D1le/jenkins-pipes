package org.myproject.jenkins

import com.github.d1le.pipeline.jenkins.BuildDef

/**
 * @author Alexey Lapin
 */
class MyProjectBuildDefs implements Serializable {

    static BuildDef deployFirstApp() {
        new BuildDef().name("deploy-first-app")
    }

    static BuildDef deploySecondApp(String version = null) {
        new BuildDef().name("deploy-second-app").parameter("version", version)
    }
}
