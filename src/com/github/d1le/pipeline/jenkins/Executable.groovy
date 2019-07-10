package com.github.d1le.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
interface Executable {

    def impl()

    def build(args)

    def checkout(args)

    def dir(String name, Closure closure)

    def emailext(args)

    def CredentialsAuth(args)

    def sh(args)

    def echo(args)

    def httpRequest(args)

    def triggerRemoteJob(args)

    def stage(name, closure)

    def parallel(args)

}
