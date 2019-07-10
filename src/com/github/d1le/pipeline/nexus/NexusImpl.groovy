package com.github.d1le.pipeline.nexus


import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class NexusImpl implements Nexus {

    String url
    String defaultRepo
    com.github.d1le.pipeline.jenkins.Cred credential

    @Override
    def download(ArtifactDef artifactDef, String path) {
        ctx().getExec().httpRequest(
            authentication: credential.id,
            ignoreSslErrors: true,
            outputFile: path,
            responseHandle: 'NONE',
            url: getUrlByArtifactDef(artifactDef))
    }

    private String getUrlByArtifactDef(ArtifactDef artifactDef) {
        def sb = new StringBuilder()
        sb.append(url)
        sb.append("service/local/artifact/maven/redirect")
        sb.append("?").append("r=${defaultRepo}")
        sb.append("&").append("g=${artifactDef.group}")
        sb.append("&").append("a=${artifactDef.id}")
        sb.append("&").append("v=${artifactDef.version}")
        if (artifactDef.type) sb.append("&").append("p=${artifactDef.type}")
        if (artifactDef.classifier) sb.append("&").append("c=${artifactDef.classifier}")
        if (artifactDef.extension) sb.append("&").append("e=${artifactDef.extension}")
        sb.toString()
    }

    @Override
    def upload(ArtifactDef artifactDef, String path) {
        return null
    }


}
