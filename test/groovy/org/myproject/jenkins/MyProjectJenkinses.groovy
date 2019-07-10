package org.myproject.jenkins

import ru.sbrf.pegi18.pipeline.jenkins.Jenkins
import ru.sbrf.pegi18.pipeline.jenkins.Jenkinses

/**
 * @author Alexey Lapin
 */
class MyProjectJenkinses extends Jenkinses {

    public static final String JENKINS_URL_CI = "https://jenkins-ci.myco.org/jenkins/"
    public static final String JENKINS_URL_STAGING = "https://jenkins-staging.myco.org/jenkins/"
    public static final String JENKINS_URL_PROD = "https://jenkins-prod.myco.org/jenkins/"

    Jenkins<MyProjectCredentials> local() {
        get(MyProjectBuildContext.ctx().getScript().env?.JENKINS_URL)
    }

    Jenkins<MyProjectCredentials> ci() {
        get(JENKINS_URL_CI)
    }

    Jenkins<MyProjectCredentials> staging() {
        get(JENKINS_URL_STAGING)
    }

    Jenkins<MyProjectCredentials> prod() {
        get(JENKINS_URL_PROD)
    }

}
