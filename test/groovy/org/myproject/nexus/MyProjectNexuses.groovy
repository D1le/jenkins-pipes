package org.myproject.nexus

import ru.sbrf.pegi18.pipeline.nexus.Nexus
import ru.sbrf.pegi18.pipeline.nexus.Nexuses

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
