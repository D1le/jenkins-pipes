package ru.sbrf.pegi18.pipeline.email

class HtmlStyleBlock implements EmailBlock {

    Script script

    HtmlStyleBlock(Script script) {
        this.script = script
    }

    @Override
    String asHtml() {
        String result = script.libraryResource 'ru/sbt/pegi18/email/style-block.html'
        return result
    }
}
