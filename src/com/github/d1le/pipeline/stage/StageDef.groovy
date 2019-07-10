package com.github.d1le.pipeline.stage

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * This container represents stage description used with {@code StageExecutor}.
 * Could be used with fluent api.
 * @see StageExecutor
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class StageDef implements Serializable {

    String name
    boolean condition = true
    Closure closure
}