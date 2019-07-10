package ru.sbrf.pegi18.pipeline.ansible


import static ru.sbrf.pegi18.pipeline.jenkins.PipelineSupport.ctx

/**
 * @author Alexey Lapin
 */
class InventoryImpl {

    Map map = [:]

    String basePath

    InventoryImpl init(InventoryDef inventoryDef) {
        def files = ctx().getScript().findFiles(glob: "${basePath}/${inventoryDef.envName}/${inventoryDef.invName}/**/*.yml")
        ctx().getScript().echo files.toString()
        for(def file : files) {
             map << ctx().getScript().readYaml(file: file.path)
        }
        this
    }


}
