package org.myproject.bitbucket

import org.myproject.jenkins.MyProjectBuildContext
import ru.sbrf.pegi18.pipeline.bitbucket.Bitbucket
import ru.sbrf.pegi18.pipeline.bitbucket.BitbucketImpl
import ru.sbrf.pegi18.pipeline.cache.CacheEntryProvider
import ru.sbrf.pegi18.pipeline.log.LoggerManager

/**
 * @author Alexey Lapin
 */
class MyProjectBitbucketsFactory implements CacheEntryProvider<String, Bitbucket> {

    public static final String PORT_SSH_DEFAULT = "7999"
    public static final String PORT_HTTP_DEFAULT = "80"
    public static final String PROJECT_DEFAULT = "my-project"

    @Override
    Bitbucket get(String key) {
        byHost(key)
    }

    private static Bitbucket byHost(String host) {
        switch (host) {
            case MyProjectBitbuckets.HOST_MYCO_BITBUCKET_CI: return new BitbucketImpl()
                .logger(LoggerManager.getLogger("BitbucketImpl"))
                .host(host)
                .sshPort(PORT_SSH_DEFAULT)
                .httpPort(PORT_HTTP_DEFAULT)
                .defaultProject(PROJECT_DEFAULT)
                .sshCred(MyProjectBuildContext.ctx().jenkins().credentials().bitbucketSshCi())
                .httpCred(MyProjectBuildContext.ctx().jenkins().credentials().bitbucketHttpCi())

            case MyProjectBitbuckets.HOST_MYCO_BITBUCKET_PROD: return new BitbucketImpl()
                .logger(LoggerManager.getLogger("BitbucketImpl"))
                .host(host)
                .sshPort(PORT_SSH_DEFAULT)
                .httpPort(PORT_HTTP_DEFAULT)
                .defaultProject(PROJECT_DEFAULT)
                .sshCred(MyProjectBuildContext.ctx().jenkins().credentials().bitbucketSshProd())
                .httpCred(MyProjectBuildContext.ctx().jenkins().credentials().bitbucketHttpProd())
            default: null
        }
    }
}
