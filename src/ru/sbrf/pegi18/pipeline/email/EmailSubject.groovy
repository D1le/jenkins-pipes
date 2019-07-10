package ru.sbrf.pegi18.pipeline.email

import com.cloudbees.groovy.cps.NonCPS

interface EmailSubject extends Serializable {

    @NonCPS
    String build()
}