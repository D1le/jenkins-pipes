package ru.sbrf.pegi18.pipeline.ansible

/**
 * @author Alexey Lapin
 */
interface Ansible {

    def play(PlaybookDef playbookDef)
}
