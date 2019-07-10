package ru.sbrf.pegi18.pipeline.email

import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.junit.Test
import ru.sbrf.pegi18.pipeline.SharedLibraryTestSupport
import ru.sbrf.pegi18.pipeline.stage.StageStatus

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * @author Alexey Lapin
 */
class StageResultsBlockTest extends SharedLibraryTestSupport{

    @Test
    void test() {
//        when(buildContext.getScript()).thenReturn()
        Script script = new Script() {
            @Override
            Object run() {
                return null
            }
        }
        RunWrapper runWrapper = mock(RunWrapper)
        when(runWrapper.getResult()).thenReturn("SUCCESS")
        script.setProperty("currentBuild", runWrapper)

        def stageResults = [
            new StageStatus(name:"checkout sources", result:"SUCCESS"),
            new StageStatus(name:"checkout config", result:"SUCCESS"),
            new StageStatus(name:"run unit tests", result:"SUCCESS"),
            new StageStatus(name:"build", result:"SUCCESS"),
            new StageStatus(name:"deploy", result:"SUCCESS"),
            new StageStatus(name:"run post checks", result:"SUCCESS"),
        ]

        def results = new StageResultsBlock(script, stageResults)
        println(results.asHtml())
    }
}
