package org.myproject.jenkins

import org.myproject.bitbucket.MyProjectBitbuckets
import org.myproject.bitbucket.MyProjectBitbucketsFactory
import org.myproject.nexus.MyProjectNexuses
import org.myproject.nexus.MyProjectNexusesFactory
import ru.sbrf.pegi18.pipeline.email.EmailSender
import ru.sbrf.pegi18.pipeline.jenkins.BuildContext
import ru.sbrf.pegi18.pipeline.jenkins.Executable
import ru.sbrf.pegi18.pipeline.jenkins.Jenkins
import ru.sbrf.pegi18.pipeline.jenkins.PipelineScriptExecutable
import ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport
import ru.sbrf.pegi18.pipeline.log.Logger
import ru.sbrf.pegi18.pipeline.log.LoggerManager
import ru.sbrf.pegi18.pipeline.stage.StageExecutor

/**
 * @author Alexey Lapin
 */
class MyProjectBuildContext implements BuildContext {

    Logger logger

    Script script
    Executable exec

    StageExecutor stageExecutor = new StageExecutor()
    EmailSender emailSender

    MyProjectJenkinses jenkinses
    MyProjectBitbuckets bitbuckets
    MyProjectNexuses nexuses

    static MyProjectBuildContext newInstance(Script script) {
        def context = new MyProjectBuildContext(script: script)
        context.setExec(new PipelineScriptExecutable(script: script))
        context
    }

    static MyProjectBuildContext ctx() {
        PipelineSupport.ctx()
    }

    Jenkins<MyProjectCredentials> jenkins() {
        jenkinses.local()
    }

    static def staged(String name, boolean condition = true, Closure closure) {
        ctx().stageExecutor.run(name, condition, closure)
    }

    @Override
    void init() {
        logger = LoggerManager.getLogger("pipe")

        stageExecutor.loggable()

        jenkinses = new MyProjectJenkinses()
        jenkinses.setProvider(new MyProjectJenkinsesFactory())

        bitbuckets = new MyProjectBitbuckets()
        bitbuckets.setProvider(new MyProjectBitbucketsFactory())

        nexuses = new MyProjectNexuses()
        nexuses.setProvider(new MyProjectNexusesFactory())

        emailSender = new EmailSender(exec: exec, replyTo: "no-reply@myco.org")
    }
}
