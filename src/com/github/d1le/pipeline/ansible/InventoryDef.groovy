package com.github.d1le.pipeline.ansible

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class InventoryDef {

    String envName
    String invName
    String branch

}
