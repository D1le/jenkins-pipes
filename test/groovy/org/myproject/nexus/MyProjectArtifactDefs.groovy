package org.myproject.nexus

import ru.sbrf.pegi18.pipeline.nexus.ArtifactDef

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
