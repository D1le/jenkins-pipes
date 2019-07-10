package com.github.d1le.pipeline.jenkins

import org.junit.BeforeClass
import org.junit.Test

import static org.mockito.Mockito.mock
import static PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
class PipelineSupportTest {


    @BeforeClass
    static void beforeClass() {
        PipelineSupport.setContext(mock(BuildContext))
    }

    @Test
    void test1() {
        println ctx()
    }

    @Test
    void test2() {
        println ctx()
    }

}
