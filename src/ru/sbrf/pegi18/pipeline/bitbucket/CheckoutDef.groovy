package ru.sbrf.pegi18.pipeline.bitbucket

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class CheckoutDef implements Serializable {

    String url
    String project
    String repo
    String branch
    String protocol
    String dir
}
