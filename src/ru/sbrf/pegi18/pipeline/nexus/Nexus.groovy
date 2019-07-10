package ru.sbrf.pegi18.pipeline.nexus

/**
 * @author Alexey Lapin
 */
interface Nexus extends Serializable {

    def download(ArtifactDef artifactDef, String path)

    def upload(ArtifactDef artifactDef, String path)

}