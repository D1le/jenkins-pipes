package ru.sbrf.pegi18.pipeline.email

import ru.sbrf.pegi18.pipeline.stage.StageStatus

import static ru.sbrf.pegi18.pipeline.email.HtmlStyles.tdStatusClass
import static ru.sbrf.pegi18.pipeline.email.HtmlStyles.trTitleClass
import static ru.sbrf.pegi18.pipeline.email.HtmlTables.trTitle

class StageResultsBlock implements EmailBlock {

    Script script
    List<StageStatus> stageResults

    StageResultsBlock(Script script, List<StageStatus> stageResults) {
        this.script = script
        this.stageResults = stageResults
    }

    @Override
    String asHtml() {
        String result = "<table class='section'>" +
            trTitle(trTitleClass(script.currentBuild.result), 2, "STAGE RESULTS")

        for (StageStatus status : stageResults) {
            result += getResultStageRow(status.name, status.result)
        }

        result += "</table>"
        result
    }

    private static String getResultStageRow(String stage, String status) {
        return "<tr><td>" + stage + "</td><td class='${tdStatusClass(status)}'>" + status + "</td></tr>"
    }
}