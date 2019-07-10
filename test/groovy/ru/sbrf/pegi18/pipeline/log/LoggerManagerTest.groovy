package ru.sbrf.pegi18.pipeline.log

import org.junit.Before
import org.junit.Test
import ru.sbrf.pegi18.pipeline.SharedLibraryTestSupport
import ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
class LoggerManagerTest extends SharedLibraryTestSupport {



    @Before
    void before() {
        PipelineSupport.setContext(buildContext)
    }
//    TODO: test PipelineEchoBasedLogger branch
//    @Test
//    void should_checkThatReturnPipelineLogger() {
//        when(buildContext.getScript()).thenReturn((Script){})
//     //   LoggerManager.getLogger(LoggerManager.name)
//        assertThat(LoggerManager.getLogger(LoggerManager.name)).isInstanceOf(PipelineEchoBasedLogger)
//    }
    @Test
    void should_returnLog4j2Logger() {
        when(buildContext.getScript()).thenReturn((Script){})
//        LoggerManager.getLogger("abc")
        assertThat(LoggerManager.getLogger("abc")).isInstanceOf(Log4j2BasedLogger)
    }

}
