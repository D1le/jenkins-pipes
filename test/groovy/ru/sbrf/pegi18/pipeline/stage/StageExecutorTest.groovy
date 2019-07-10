package ru.sbrf.pegi18.pipeline.stage

import hudson.model.Result
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import ru.sbrf.pegi18.pipeline.PipelineException
import ru.sbrf.pegi18.pipeline.SharedLibraryTestSupport
import ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport
import ru.sbrf.pegi18.pipeline.log.Log4j2BasedLogger
import ru.sbrf.pegi18.pipeline.log.Logger
import ru.sbrf.pegi18.pipeline.log.LoggerManager

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.tuple
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
class StageExecutorTest extends SharedLibraryTestSupport {

    public static final String CONST_NAME = "name"
    public static final String CONST_RESULT = "result"
    public static final String CONST_CURRENT_BUILD = "currentBuild"

    private StageExecutor executor

    @Before
    void before() {
        PipelineSupport.setContext(buildContext)

        when(buildContext.getExec().stage(any(), any())).thenAnswer({ mock -> mock.getArgument(1).call() })

        Logger logger = mock(Logger)
//        when(logger.debug(any())).thenAnswer({ mock -> println(mock.getArgument(0)) })
//        when(logger.error(any())).thenAnswer({ mock -> println(mock.getArgument(0)) })

        executor = new StageExecutor()
        executor.setLogger(logger)
    }

    @Test
    void should_resultsHaveSuccessStatus_when_emptyClosure() {
        executor.run(CONST_NAME, {})

//        println executor.getResults().collect({ "${it.name}: ${it.result}" })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple(CONST_NAME, StageExecutor.STATUS_SUCCESS))
    }

    @Test
    void should_resultsHaveSkippedStatus_when_emptyClosureAndConditionFalse() {
        executor.run(CONST_NAME, false, {})

//        println executor.getResults().collect({ "${it.name}: ${it.result}" })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple(CONST_NAME, StageExecutor.STATUS_SKIPPED))
    }

    @Test
    void should_executorReturnValueFormClosure() {
        int res = executor.run(CONST_NAME, {
            19
        })

        assertThat(res).isEqualTo(19)
    }

    @Test
    void should_resultsHaveFailureStatus_when_closureReturnsFailureStatus() {
        executor.run(CONST_NAME, {
            Result.FAILURE
        })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple("name", StageExecutor.STATUS_FAILURE))
    }

    @Test
    void should_resultsHaveUnstableStatus_when_closureReturnsUnstableStatus() {
        executor.run(CONST_NAME, {
            Result.UNSTABLE
        })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple("name", StageExecutor.STATUS_UNSTABLE))
    }

    @Test
    void should_resultsHaveFailedStatus_when_closureThrows() {

        RunWrapper run = mock(RunWrapper, Answers.RETURNS_DEEP_STUBS)
        when(run.getRawBuild().getAction(any(Class))).thenReturn(false)

        Script script = {}
        script.setProperty(CONST_CURRENT_BUILD, run)
        when(buildContext.getScript()).thenReturn(script)

        try {
            executor.run(CONST_NAME, {
                throw new PipelineException()
            })
        } catch (Throwable t) {
            //expected
        }

//        println executor.getResults().collect({ "${it.name}: ${it.result}" })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple(CONST_NAME, StageExecutor.STATUS_FAILED))
    }

    @Test
    void should_resultsHaveAbortedStatus_when_closureThrows() {

        RunWrapper run = mock(RunWrapper, Answers.RETURNS_DEEP_STUBS)
        when(run.getRawBuild().getAction(any(Class))).thenReturn(true)

        Script script = {}
        script.setProperty(CONST_CURRENT_BUILD, run)
        when(buildContext.getScript()).thenReturn(script)

        try {
            executor.run(CONST_NAME, {
                throw new PipelineException()
            })
        } catch (Throwable t) {
            //expected
        }

//        println executor.getResults().collect({ "${it.name}: ${it.result}" })
        assertThat(executor.getResults()).extracting(CONST_NAME, CONST_RESULT).containsOnly(tuple(CONST_NAME, StageExecutor.STATUS_ABORTED))
    }
    @Test
    void should_loggableInitializesLogger(){
        when(buildContext.getScript()).thenReturn((Script){})
//        println (ctx().getScript().getClass().getName())
        executor.setLogger(null)

        println executor.loggable()
        assertThat(executor.getLogger()).isNotNull()
    }
}
