package ru.sbrf.pegi18.pipeline.jenkins

import ru.sbrf.pegi18.pipeline.cache.Cache

/**
 * @author Alexey Lapin
 */
class Credentials extends Cache<String, Cred> {

    public static String CRED_JENKINS = "JENKINS"

    Cred jenkins() {
        get(CRED_JENKINS)
    }

}
