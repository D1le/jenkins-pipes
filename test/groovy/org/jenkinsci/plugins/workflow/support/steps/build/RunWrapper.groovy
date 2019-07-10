package org.jenkinsci.plugins.workflow.support.steps.build

import hudson.AbortException
import hudson.model.Run

class RunWrapper {

    String result
    String fullDisplayName
    int number

    public String getResult() throws AbortException {
        result
    }

    public String getFullDisplayName() throws AbortException {
        fullDisplayName
    }

    public int getNumber() throws AbortException {
        number
    }

    Run getRawBuild() throws AbortException {
        null
    }
}