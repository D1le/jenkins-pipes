package ru.sbrf.pegi18.pipeline.email

class HtmlStyles {

    static String trTitleClass(String status) {
        return "tr-title-" + status.toLowerCase()
    }

    static String tdStatusClass(String status) {
        return "td-" + status.toLowerCase()
    }
}
