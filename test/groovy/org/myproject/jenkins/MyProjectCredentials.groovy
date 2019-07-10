package org.myproject.jenkins

import com.github.d1le.pipeline.jenkins.Cred
import com.github.d1le.pipeline.jenkins.Credentials

/**
 * @author Alexey Lapin
 */
class MyProjectCredentials extends Credentials {

    public static String CRED_TECH = "TECH"
    public static String CRED_BITBUCKET_SSH_CI = "BITBUCKET_SSH_CI"
    public static String CRED_BITBUCKET_HTTP_CI = "BITBUCKET_HTTP_CI"
    public static String CRED_BITBUCKET_SSH_PROD = "BITBUCKET_SSH_PROD"
    public static String CRED_BITBUCKET_HTTP_PROD = "BITBUCKET_HTTP_PROD"
    public static String CRED_NEXUS_CI = "NEXUS_CI"
    public static String CRED_NEXUS_PROD = "NEXUS_PROD"

    Cred tech() {
        get(CRED_TECH)
    }

    Cred bitbucketSshCi() {
        get(CRED_BITBUCKET_SSH_CI)
    }

    Cred bitbucketSshProd() {
        get(CRED_BITBUCKET_SSH_PROD)
    }

    Cred bitbucketHttpCi() {
        get(CRED_BITBUCKET_HTTP_CI)
    }

    Cred bitbucketHttpProd() {
        get(CRED_BITBUCKET_HTTP_PROD)
    }

    Cred nexusCi() {
        get(CRED_NEXUS_CI)
    }

    Cred nexusProd() {
        get(CRED_NEXUS_PROD)
    }
}
