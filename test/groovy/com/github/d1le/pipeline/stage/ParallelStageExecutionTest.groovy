package com.github.d1le.pipeline.stage


import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static ParallelStageExecution.stagedParallel

/**
 * @author Alexey Lapin
 */
class ParallelStageExecutionTest {

//    @Captor
    ArgumentCaptor captor

    private com.github.d1le.pipeline.jenkins.Executable exec
    private com.github.d1le.pipeline.jenkins.BuildContext buildContext

    @Before
    void before() {
        buildContext = mock(com.github.d1le.pipeline.jenkins.BuildContext)
        exec = mock(com.github.d1le.pipeline.jenkins.Executable)
        when(buildContext.getExec()).thenReturn(exec)
        com.github.d1le.pipeline.jenkins.PipelineSupport.setContext(buildContext)
    }

    @Test
    void test() {
        println stagedParallel()
    }

    @Test
    void test2() {
        println(exec)
        captor = ArgumentCaptor.forClass(Map)

        StageExecutor stageExecutor = mock(StageExecutor)
        when(buildContext.getStageExecutor()).thenReturn(new StageExecutor())
        when(exec.stage(any(), any())).thenAnswer({
            invocationOnMock -> invocationOnMock.getArgument(1)()
        })

        stagedParallel()
//            .stageExecutor()
            .stage("stage1", {
                println("from stage1")
            })
            .stage("stage2", {
                println("from stage2")
            })
            .waitStages()

        verify(exec).parallel(captor.capture())

        def value = captor.getValue()
        println(value)

        def c = value.get("stage1")
        c()

    }
}
