package com.github.d1le.pipeline.ansible


import static com.github.d1le.pipeline.jenkins.PipelineSupport.ctx

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
