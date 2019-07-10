package com.github.d1le.pipeline.email

class HtmlTables {

    static String trTitle(String trClass, int colspan = 1, String title) {
        return "<tr class='${trClass}'><td class='td-title' colspan=${colspan}>${title}</td></tr>"
    }

    static String trMainTitle(String trClass, int colspan = 1, String title) {
        return "<tr class='${trClass}'><td class='td-title-main' colspan=${colspan}>${title}</td></tr>"
    }
}
