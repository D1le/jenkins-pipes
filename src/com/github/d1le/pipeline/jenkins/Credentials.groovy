package com.github.d1le.pipeline.jenkins

import com.github.d1le.pipeline.cache.Cache

/**
 * @author Alexey Lapin
 */
class Credentials extends Cache<String, Cred> {

    public static String CRED_JENKINS = "JENKINS"

    Cred jenkins() {
        get(CRED_JENKINS)
    }

}
