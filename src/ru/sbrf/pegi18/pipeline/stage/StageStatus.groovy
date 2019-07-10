package ru.sbrf.pegi18.pipeline.stage

/**
 * @author Alexey Lapin
 */
class StageStatus implements Serializable {
    String name
    String result

    static StageStatus of(StageDef stageDef, String result) {
        new StageStatus(name: stageDef.name, result: result)
    }
}
