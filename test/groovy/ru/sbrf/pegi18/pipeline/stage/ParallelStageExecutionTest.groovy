package ru.sbrf.pegi18.pipeline.stage

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import ru.sbrf.pegi18.pipeline.jenkins.BuildContext
import ru.sbrf.pegi18.pipeline.jenkins.Executable
import ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static ru.sbrf.pegi18.pipeline.stage.ParallelStageExecution.stagedParallel

/**
 * @author Alexey Lapin
 */
class ParallelStageExecutionTest {

//    @Captor
    ArgumentCaptor captor

    private Executable exec
    private BuildContext buildContext

    @Before
    void before() {
        buildContext = mock(BuildContext)
        exec = mock(Executable)
        when(buildContext.getExec()).thenReturn(exec)
        PipelineSupport.setContext(buildContext)
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
