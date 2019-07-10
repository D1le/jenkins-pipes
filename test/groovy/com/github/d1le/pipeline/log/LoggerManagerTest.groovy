package com.github.d1le.pipeline.log


import org.junit.Before
import org.junit.Test
import com.github.d1le.pipeline.SharedLibraryTestSupport

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when
class LoggerManagerTest extends SharedLibraryTestSupport {



    @Before
    void before() {
        com.github.d1le.pipeline.jenkins.PipelineSupport.setContext(buildContext)
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
