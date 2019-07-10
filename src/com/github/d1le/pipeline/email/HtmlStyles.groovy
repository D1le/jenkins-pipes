package com.github.d1le.pipeline.email

class HtmlStyles {

    static String trTitleClass(String status) {
        return "tr-title-" + status.toLowerCase()
    }

    static String tdStatusClass(String status) {
        return "td-" + status.toLowerCase()
    }
}
