package org.myproject.jenkins

import org.myproject.bitbucket.MyProjectBitbuckets
import org.myproject.bitbucket.MyProjectBitbucketsFactory
import org.myproject.nexus.MyProjectNexuses
import org.myproject.nexus.MyProjectNexusesFactory
import com.github.d1le.pipeline.email.EmailSender
import com.github.d1le.pipeline.jenkins.BuildContext
import com.github.d1le.pipeline.jenkins.Executable
import com.github.d1le.pipeline.jenkins.Jenkins
import com.github.d1le.pipeline.jenkins.PipelineScriptExecutable
import com.github.d1le.pipeline.jenkins.PipelineSupport
import com.github.d1le.pipeline.log.Logger
import com.github.d1le.pipeline.log.LoggerManager
import com.github.d1le.pipeline.stage.StageExecutor

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
