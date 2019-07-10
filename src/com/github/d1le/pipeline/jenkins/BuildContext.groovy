package com.github.d1le.pipeline.jenkins

import com.github.d1le.pipeline.stage.StageExecutor

/**
 * @author Alexey Lapin
 */
interface BuildContext {

    void init()

    Script getScript()

    Executable getExec()

    StageExecutor getStageExecutor()

}