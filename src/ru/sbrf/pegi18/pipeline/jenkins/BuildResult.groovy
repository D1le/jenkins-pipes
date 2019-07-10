package ru.sbrf.pegi18.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
interface BuildResult<T> {

    T impl()

    String status()
}