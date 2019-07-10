package ru.sbrf.pegi18.pipeline

import net.sf.json.JSON
import net.sf.json.groovy.JsonSlurper
import org.junit.Rule
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import ru.sbrf.pegi18.pipeline.jenkins.BuildContext

/**
 * @author Alexey Lapin
 */
class SharedLibraryTestSupport {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule()

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    protected BuildContext buildContext

    static String resText(String resource) {
        getClass().getResource(resource).getText()
    }

    static String resPath(String resource) {
        getClass().getResource(resource).getPath()
    }

    static JSON resJson(String resource) {
        new JsonSlurper().parseText(resText(resource))
    }

}
