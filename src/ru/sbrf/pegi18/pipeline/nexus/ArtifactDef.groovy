package ru.sbrf.pegi18.pipeline.nexus

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ArtifactDef implements Serializable {

    public static final String TYPE_ZIP = "zip"
    public static final String EXTENSION_POM = "pom"

    String group
    String id
    String version
    String classifier
    String type
    String extension
}
