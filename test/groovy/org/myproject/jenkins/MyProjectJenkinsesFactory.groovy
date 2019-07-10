package org.myproject.jenkins

import com.cloudbees.groovy.cps.NonCPS
import com.github.d1le.pipeline.cache.CacheEntryProvider
import com.github.d1le.pipeline.jenkins.AbstractJenkins
import com.github.d1le.pipeline.jenkins.Cred
import com.github.d1le.pipeline.jenkins.Credentials
import com.github.d1le.pipeline.jenkins.Jenkins
import com.github.d1le.pipeline.jenkins.LocalJenkins
import com.github.d1le.pipeline.jenkins.RemoteJenkins

/**
 * @author Alexey Lapin
 */
class MyProjectJenkinsesFactory implements CacheEntryProvider<String, Jenkins> {

    private Map<StringPair, Cred> jenkinsToJenkinsCredMatrix = new HashMap<>()

    @Override
    Jenkins get(String key) {
        byUrl(key)
    }

    static boolean isLocal(String url) {
        url?.equals(MyProjectBuildContext.ctx().getScript().env?.JENKINS_URL)
    }

    Jenkins byUrl(String url) {
        switch (url) {
            case MyProjectJenkinses.JENKINS_URL_CI: return instantiate(url).baseFolder("job/myco-project/")
            case MyProjectJenkinses.JENKINS_URL_STAGING: return instantiate(url).baseFolder("job/myco/job/myco-project/")
            case MyProjectJenkinses.JENKINS_URL_PROD: return instantiate(url).baseFolder("job/myco/job/myco-project/")
            default: null
        }
    }

    AbstractJenkins<MyProjectCredentials> instantiate(String url) {
        AbstractJenkins<MyProjectCredentials> jenkins
        if (isLocal(url)) {
            jenkins = new LocalJenkins<>()
        } else {
            jenkins = new RemoteJenkins<>()
        }
        jenkins.url(url).credentials(credentialsByUrl(url)).exec(MyProjectBuildContext.ctx().getExec())
        jenkins
    }

    MyProjectCredentials credentialsByUrl(String url) {
        def credentials = new MyProjectCredentials()
        credentials.put(Credentials.CRED_JENKINS, jenkinsCredentialByUrl(url))
        switch (url) {
            case MyProjectJenkinses.JENKINS_URL_CI:
                credentials.put(MyProjectCredentials.CRED_TECH, new Cred(id: "tech"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_SSH_CI, new Cred(id: "bitbucket-ci-ssh"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_HTTP_CI, new Cred(id: "bitbucket-ci-http"))
                credentials.put(MyProjectCredentials.CRED_NEXUS_CI, new Cred(id: "nexus-ci"))
                credentials.put(MyProjectCredentials.CRED_NEXUS_PROD, new Cred(id: "nexus-prod"))

                return credentials
            case MyProjectJenkinses.JENKINS_URL_STAGING:
                credentials.put(MyProjectCredentials.CRED_TECH, new Cred(id: "tech"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_SSH_PROD, new Cred(id: "bitbucket-prod-ssh"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_HTTP_PROD, new Cred(id: "bitbucket-prod-http"))
                credentials.put(MyProjectCredentials.CRED_NEXUS_PROD, new Cred(id: "nexus-prod"))
                return credentials
            case MyProjectJenkinses.JENKINS_URL_PROD:
                credentials.put(MyProjectCredentials.CRED_TECH, new Cred(id: "tech"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_SSH_PROD, new Cred(id: "bitbucket-prod-ssh"))
                credentials.put(MyProjectCredentials.CRED_BITBUCKET_HTTP_PROD, new Cred(id: "bitbucket-prod-http"))
                credentials.put(MyProjectCredentials.CRED_NEXUS_PROD, new Cred(id: "nexus-prod"))
                return credentials
            default: null
        }
    }

    private Cred jenkinsCredentialByUrl(String url) {
        getMatrix().get(StringPair.of(MyProjectBuildContext.ctx().getScript().env?.JENKINS_URL, url))
    }

    static class StringPair implements Serializable {
        String k1
        String k2

        @Override
        @NonCPS
        String toString() {
            "$k1:$k2"
        }

        @Override
        @NonCPS
        int hashCode() {
            toString().hashCode()
        }

        @Override
        @NonCPS
        boolean equals(Object obj) {
            if (obj instanceof StringPair) {
                return obj.k1 == this.k1 && obj.k2 == this.k2
            }
            false
        }

        static StringPair of(String k1, k2) {
            new StringPair(k1: k1, k2: k2)
        }
    }

    private getMatrix() {
        if (jenkinsToJenkinsCredMatrix.isEmpty()) {
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_CI, MyProjectJenkinses.JENKINS_URL_CI), new Cred(id: "jenkins-ci"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_CI, MyProjectJenkinses.JENKINS_URL_STAGING), new Cred(id: "jenkins-staging"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_CI, MyProjectJenkinses.JENKINS_URL_PROD), new Cred(id: "jenkins-prod"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_STAGING, MyProjectJenkinses.JENKINS_URL_CI), new Cred(id: "jenkins-ci"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_STAGING, MyProjectJenkinses.JENKINS_URL_STAGING), new Cred(id: "jenkins-staging"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_STAGING, MyProjectJenkinses.JENKINS_URL_PROD), new Cred(id: "jenkins-prod"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_PROD, MyProjectJenkinses.JENKINS_URL_CI), new Cred(id: "jenkins-ci"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_PROD, MyProjectJenkinses.JENKINS_URL_STAGING), new Cred(id: "jenkins-staging"))
            jenkinsToJenkinsCredMatrix.put(StringPair.of(MyProjectJenkinses.JENKINS_URL_PROD, MyProjectJenkinses.JENKINS_URL_PROD), new Cred(id: "jenkins-prod"))
        }
        jenkinsToJenkinsCredMatrix
    }
}
