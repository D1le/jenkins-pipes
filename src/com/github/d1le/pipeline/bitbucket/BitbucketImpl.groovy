package com.github.d1le.pipeline.bitbucket

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import com.github.d1le.pipeline.jenkins.Cred
import com.github.d1le.pipeline.log.Logger

import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class BitbucketImpl implements Bitbucket {

    Logger logger

    String host
    String sshPort
    String httpPort
    String defaultProject
    Cred sshCred
    Cred httpCred

    @Override
    Map checkout(CheckoutDef checkoutDef) {
        logger.debug("checkouting ${getUrlByCheckoutDef(checkoutDef)}")
        if (checkoutDef.dir) {
            (Map) ctx().getExec().dir(checkoutDef.repo) {
                checkoutClosure(checkoutDef).call()
            }
        } else {
            (Map) checkoutClosure(checkoutDef).call()
        }
    }

    private Closure checkoutClosure(CheckoutDef checkoutDef) {
        return {
            ctx().getExec().checkout(
                [$class                           : 'GitSCM',
                 branches                         : [[name: "${checkoutDef.branch}"]],
                 doGenerateSubmoduleConfigurations: false,
                 extensions                       : [],
                 submoduleCfg                     : [],
                 userRemoteConfigs                : [[credentialsId: getCredByCheckoutDef(checkoutDef).id, url: getUrlByCheckoutDef(checkoutDef)]]]
            )
        }
    }

    private String getUrlByCheckoutDef(CheckoutDef checkoutDef) {
        String url = null
        if (checkoutDef.url) {
            url = checkoutDef.url
        } else {
            def sb = new StringBuilder()
            sb.append(checkoutDef.protocol).append("://").append(getPrefixByCheckoutDef(checkoutDef)).append(host).append(":").append(getPortByCheckoutDef(checkoutDef))
            sb.append("/")
            sb.append(getProjectByCheckoutDef(checkoutDef))
            sb.append("/")
            sb.append(checkoutDef.repo)
            sb.append(getExtensionByCheckoutDef(checkoutDef))
            sb.toString()
        }
    }

    private Cred getCredByCheckoutDef(CheckoutDef checkoutDef) {
        if (PROTOCOL_SSH.equals(checkoutDef.protocol)) {
            sshCred
        } else {
            httpCred
        }
    }

    private String getExtensionByCheckoutDef(CheckoutDef checkoutDef) {
        if (PROTOCOL_SSH.equals(checkoutDef.protocol)) {
            ".git"
        } else {
            ""
        }

    }

    private String getProjectByCheckoutDef(CheckoutDef checkoutDef) {
        if (checkoutDef.project) {
            checkoutDef.project
        } else {
            defaultProject
        }
    }

    private String getPortByCheckoutDef(CheckoutDef checkoutDef) {
        if (PROTOCOL_SSH.equals(checkoutDef.protocol)) {
            sshPort
        } else {
            httpPort
        }
    }

    private String getPrefixByCheckoutDef(CheckoutDef checkoutDef) {
        if (PROTOCOL_SSH.equals(checkoutDef.protocol)) {
            "git@"
        } else {
            ""
        }
    }
}
