package com.github.d1le.pipeline.ansible

/**
 * @author Alexey Lapin
 */
interface Ansible {

    def play(PlaybookDef playbookDef)
}
