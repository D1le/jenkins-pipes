package ru.sbrf.pegi18.pipeline.jenkins

import ru.sbrf.pegi18.pipeline.stage.StageExecutor

/**
 * @author Alexey Lapin
 */
interface BuildContext {

    void init()

    Script getScript()

    Executable getExec()

    StageExecutor getStageExecutor()

}