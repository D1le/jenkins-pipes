package com.github.d1le.pipeline.email

class HtmlStyleBlock implements EmailBlock {

    Script script

    HtmlStyleBlock(Script script) {
        this.script = script
    }

    @Override
    String asHtml() {
        String result = script.libraryResource 'com/github/d1le/pipeline/email/style-block.html'
        return result
    }
}
