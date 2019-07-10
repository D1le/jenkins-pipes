package ru.sbrf.pegi18.pipeline.jenkins

/**
 * @author Alexey Lapin
 */
class PipelineScriptExecutable implements Executable, Serializable {

    Script script

    @Override
    def impl() {
        script
    }

    @Override
    def build(Object args) {
        script.build(args)
    }

    @Override
    def checkout(Object args) {
        script.checkout(args)
    }

    @Override
    def dir(String name, Closure closure) {
        script.dir(name, closure)
    }

    @Override
    def emailext(Object args) {
        script.emailext(args)
    }

    @Override
    def CredentialsAuth(Object args) {
        script.CredentialsAuth(args)
    }

    @Override
    def sh(Object args) {
        script.sh(args)
    }

    @Override
    def echo(Object args) {
        script.echo(args)
    }

    @Override
    def httpRequest(Object args) {
        script.httpRequest(args)
    }

    @Override
    def triggerRemoteJob(Object args) {
        script.triggerRemoteJob(args)
    }

    @Override
    def stage(name, closure) {
        script.stage(name, closure)
    }

    @Override
    def parallel(Object args) {
        script.parallel(args)
    }
}
