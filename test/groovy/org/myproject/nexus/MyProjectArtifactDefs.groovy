package org.myproject.nexus

import com.github.d1le.pipeline.nexus.ArtifactDef

/**
 * @author Alexey Lapin
 */
class MyProjectArtifactDefs {

    static ArtifactDef firstApp(String version) {
        new ArtifactDef().group("org.myco.team1").id("first-app").version(version).type(ArtifactDef.TYPE_ZIP)
    }

    static ArtifactDef secondApp(String version) {
        new ArtifactDef().group("org.myco.team2").id("second-app").version(version).type(ArtifactDef.TYPE_ZIP)
    }
}
