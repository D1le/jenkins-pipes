package org.myproject.nexus

import com.github.d1le.pipeline.nexus.Nexus
import com.github.d1le.pipeline.nexus.Nexuses

/**
 * @author Alexey Lapin
 */
class MyProjectNexuses extends Nexuses {

    public static final String NEXUS_URL_CI = "http://nexus-ci.myco.org:8081/nexus/"
    public static final String NEXUS_URL_PROD = "http://nexus-prod.myco.org/nexus/"

    Nexus ci() {
        get(NEXUS_URL_CI)
    }

    Nexus prod() {
        get(NEXUS_URL_PROD)
    }
}
