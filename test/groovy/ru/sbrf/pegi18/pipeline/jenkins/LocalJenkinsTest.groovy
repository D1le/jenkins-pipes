package ru.sbrf.pegi18.pipeline.jenkins

import hudson.AbortException
import hudson.model.Result
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import ru.sbrf.pegi18.pipeline.PipelineException
import ru.sbrf.pegi18.pipeline.SharedLibraryTestSupport

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.assertThatThrownBy
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

/**
 * @author Alexey Lapin
 */
class LocalJenkinsTest extends SharedLibraryTestSupport {

    private static final String SOME_STRING = "some-string"

    LocalJenkins jenkins
    RunWrapper runWrapper

    @Captor
    private ArgumentCaptor captor

    @Before
    void before() {
        jenkins = new LocalJenkins(exec: buildContext.getExec())
        runWrapper = mock(RunWrapper)
    }

    @Test
    void should_buildGetProperParameters() {
        when(buildContext.getExec().build(any())).thenReturn(mock(RunWrapper))

        jenkins.newBuild(new BuildDef().name(SOME_STRING))

        verify(buildContext.getExec()).build(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("job", SOME_STRING).containsEntry("wait", true)
    }

    @Test
    void should_buildGetProperFieldParameters() {
        when(buildContext.getExec().build(any())).thenReturn(mock(RunWrapper))
        Map<String, String> someMap = new HashMap()
        someMap.put("some", "map")
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true).parameter("abc", someMap)

        jenkins.newBuild(buildDef)

        verify(buildContext.getExec()).build(captor.capture())
        assertThat((Map) captor.getValue()).containsEntry("parameters", [someMap])
    }

    @Test
    void should_throwPipelineException_when_BuildThrows() {
        when(buildContext.getExec().build(any())).thenThrow(new PipelineException())
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(PipelineException)
    }

    @Test
    void should_returnNull_whenJenkinsApiCalls() {
        assertThat(jenkins.api()).isNull()
    }

    @Test
    void should_returnInstanceOfRunWrapperBasedBuildResult_when_propagateErrorsFalseAndBuildNull() {
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: false)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RunWrapperBasedBuildResult)
    }

    @Test
    void should_returnInstanceOfRunWrapperBasedBuildResult_when_buildIsNull() {
        when(buildContext.getExec().build(any())).thenReturn(null)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RunWrapperBasedBuildResult)
    }

    @Test
    void should_throwAbortedException_when_runWrapperReturnsFailure() {
        when(runWrapper.getResult()).thenReturn(Result.FAILURE.toString())
        when(buildContext.getExec().build(any())).thenReturn(runWrapper)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(AbortException)
    }

    @Test
    void should_throwAbortedException_when_runWrapperReturnsUnstable() {
        when(runWrapper.getResult()).thenReturn(Result.UNSTABLE.toString())
        when(buildContext.getExec().build(any())).thenReturn(runWrapper)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: true)

        assertThatThrownBy({ jenkins.newBuild(buildDef) }).isInstanceOf(AbortException)
    }

    @Test
    void should_returnInstanceOfRunWrapperBasedBuildResult_when_propagateErrorsFalse() {
        when(runWrapper.getResult()).thenReturn(SOME_STRING)
        when(buildContext.getExec().build(any())).thenReturn(runWrapper)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: false, propagateUnstableAsFailure: true)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RunWrapperBasedBuildResult)
    }

    @Test
    void should_returnInstanceOfRunWrapperBasedBuildResult_when_buildReturnsSomeString() {
        when(runWrapper.getResult()).thenReturn(SOME_STRING)
        when(buildContext.getExec().build(any())).thenReturn(runWrapper)
        BuildDef buildDef = new BuildDef(name: SOME_STRING, propagateErrors: true, propagateUnstableAsFailure: true)

        assertThat(jenkins.newBuild(buildDef)).isInstanceOf(RunWrapperBasedBuildResult)
    }

    @Test
    void should_returnInstanceOfRunWrapperBasedBuildResult_when_buildDefIsNull() {
        when(buildContext.getExec().build(any())).thenReturn(runWrapper)

        verify(runWrapper, never()).getResult()
    }
}
