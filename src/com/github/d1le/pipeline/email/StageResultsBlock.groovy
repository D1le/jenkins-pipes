package com.github.d1le.pipeline.email


import static HtmlStyles.tdStatusClass
import static HtmlStyles.trTitleClass
import static HtmlTables.trTitle

class StageResultsBlock implements EmailBlock {

    Script script
    List<com.github.d1le.pipeline.stage.StageStatus> stageResults

    StageResultsBlock(Script script, List<com.github.d1le.pipeline.stage.StageStatus> stageResults) {
        this.script = script
        this.stageResults = stageResults
    }

    @Override
    String asHtml() {
        String result = "<table class='section'>" +
            trTitle(trTitleClass(script.currentBuild.result), 2, "STAGE RESULTS")

        for (com.github.d1le.pipeline.stage.StageStatus status : stageResults) {
            result += getResultStageRow(status.name, status.result)
        }

        result += "</table>"
        result
    }

    private static String getResultStageRow(String stage, String status) {
        return "<tr><td>" + stage + "</td><td class='${tdStatusClass(status)}'>" + status + "</td></tr>"
    }
}