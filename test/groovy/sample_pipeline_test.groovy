import com.lesfurets.jenkins.unit.BasePipelineTest
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.myproject.jenkins.MyProjectJenkinses

import static org.mockito.Mockito.mock

class sample_pipeline_test extends BasePipelineTest {

    @Before
    void before() {
        setUp()
        helper.registerAllowedMethod("ansiColor", [String, Closure], { args -> args[1]() })
        helper.registerAllowedMethod("build", [Map], {})
        helper.registerAllowedMethod("CredentialsAuth", [Map], {})
        helper.registerAllowedMethod("emailext", [Map], {})
        helper.registerAllowedMethod("httpRequest", [Map], {})
        helper.registerAllowedMethod("timestamps", [Closure], { args -> args() })
        helper.registerAllowedMethod("triggerRemoteJob", [Map], {})

        def env = new Binding()
        env.setProperty("JENKINS_URL", MyProjectJenkinses.JENKINS_URL_CI)

        binding.setProperty("env", env);
        binding.setProperty("currentBuild", mock(RunWrapper, Answers.RETURNS_DEEP_STUBS))
    }

    @Test
    void name() {
        runScript("test/groovy/sample_pipeline.groovy")
    }
}