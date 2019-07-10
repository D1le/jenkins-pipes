package com.github.d1le.pipeline.email

import com.cloudbees.groovy.cps.NonCPS

interface EmailSubject extends Serializable {

    @NonCPS
    String build()
}