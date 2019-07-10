package com.github.d1le.pipeline.jenkins


import hudson.AbortException
import hudson.model.Result
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.assertThatThrownBy
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

/**
 * @author Alexey Lapin
 */
class RemoteJenkinsTest extends com.github.d1le.pipeline.SharedLibraryTestSupport {

    private static final String SOME_STRING = "some-string"

    private RemoteJenkins jenkins
    private Credentials credentials = new Credentials()
    private Cred cred = new Cred(id: SOME_STRING)
    RemoteBuildHandle remoteBuildHandle

    @Captor
    private ArgumentCaptor captor

    @Before
    void before() {
        credentials.put(Credentials.CRED_JENKINS, cred)

        jenkins = new RemoteJenkins<Credentials>(exec: buildContext.getExec(), credentials: credentials)

        remoteBuildHandle = mock(RemoteBuildHandle)
    }

    @Test
    void should_buildGetProperParameters() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        Map<String, String> someMap = new HashMap()
        someMap.put("name", "abc")
        someMap.put("value", "map")
        Map<String, String> someMap1 = new HashMap()
        someMap1.put("name", "cde")
        someMap1.put("value", "map1")
        buildDef.parameter("abc", someMap)
        buildDef.parameter("cde", someMap1)

        jenkins.newBuild(buildDef)

        verify(buildContext.getExec()).triggerRemoteJob(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("parameters", "abc=map\ncde=map1")
    }

    @Test
    void should_returnProperJob() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        Credentials credentials = new Credentials()
        credentials.put("JENKINS", new Cred(id: "qwer"))

        jenkins.credentials(credentials).exec(buildContext.getExec()).url("http://jenkins/").baseFolder("job/folder/")
        jenkins.newBuild(new BuildDef().name("zxcv/asdf"))

        verify(buildContext.getExec()).triggerRemoteJob(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("job", "${''}http://jenkins/job/folder/job/zxcv/job/asdf")
    }

    @Test
    void should_returnNull_whenJenkinsApiCalls() {
        assertThat(jenkins.api()).isNull()
    }

    @Test
    void should_buildGetProperBlockBuildUntilComplete() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)

        jenkins.newBuild(new BuildDef().name(SOME_STRING))

        verify(buildContext.getExec()).triggerRemoteJob(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("blockBuildUntilComplete", true)
    }

    @Test
    void should_buildGetProperCredentials() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)

        jenkins.newBuild(new BuildDef().name(SOME_STRING))

        verify(buildContext.getExec()).CredentialsAuth(credentials: SOME_STRING)
    }

    @Test
    void should_buildGetProperPropagateErrors() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)

        jenkins.newBuild(new BuildDef().name(SOME_STRING))

        verify(buildContext.getExec()).triggerRemoteJob(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("shouldNotFailBuild", true)
    }

    @Test
    void should_throwPipeleneException() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenThrow(new com.github.d1le.pipeline.PipelineException())
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(com.github.d1le.pipeline.PipelineException)
    }

    @Test
    void should_returnNewRemoteHandleBasedBuildResult_when_propagateErrorsFalse() {
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: false)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RemoteHandleBasedBuildResult)
    }

    @Test
    void should_throwNewAbortedException_when_remoteBuildHandleReturnsFailure() {
        when(remoteBuildHandle.getBuildStatus()).thenReturn(Result.FAILURE.toString())
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(AbortException)
    }

    @Test
    void should_throwNewAbortedException_when_remoteBuildHandleReturnsUnstable() {
        when(remoteBuildHandle.getBuildStatus()).thenReturn(Result.UNSTABLE.toString())
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(AbortException)
    }

    @Test
    void should_returnNewRemoteHandleBasedBuildResult_when_remoteBuildHandleReturnsSomeString() {
        when(remoteBuildHandle.getBuildStatus()).thenReturn(SOME_STRING)
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: true)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RemoteHandleBasedBuildResult)
    }

    @Test
    void should_returnNewRemoteHandleBasedBuildResult_when_propagateUnstableAsFailureFalse() {
        when(remoteBuildHandle.getBuildStatus()).thenReturn(Result.UNSTABLE.toString())
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(remoteBuildHandle)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: false)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RemoteHandleBasedBuildResult)
    }

    @Test
    void should_returnNewRemoteHandleBasedBuildResult_when_triggerRemoteJobReturnsNull() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenReturn(null)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: false)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RemoteHandleBasedBuildResult)
    }

    @Test
    void should_returnNewRemoteHandleBasedBuildResult_when_propagateErrorsFalse1() {
        when(buildContext.getExec().triggerRemoteJob(any())).thenThrow(new com.github.d1le.pipeline.PipelineException())
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: false)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RemoteHandleBasedBuildResult)
    }

    interface RemoteBuildHandle {
        String getBuildStatus()

        String getJobFullDisplayName()
    }
}
