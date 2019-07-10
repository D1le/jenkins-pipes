package com.github.d1le.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
interface BuildResult<T> {

    T impl()

    String status()
}