package com.wemape.subject.core;

public class Parser {
    private static final String REG_REMOVE_SPECIAL = "[\"'\\{\\}\\[\\]/?.,;:|\\)\\(*~`!^\\-_+<>@#$%&^\\\\=]";
    private static final String REG_ENG = "[^a-zA-z]";
    private static final String REG_NUM = "[^0-9]";
    private static final String REG_REMOVE_WHITESPACE = "(\r\n|\r|\n|\n\r|\\p{Z}|\\t)";
    private static final String REG_REMOVE_TAG = "<[^>]*>";

    public static String removeSpecial(String html) {
        return html.replaceAll(REG_REMOVE_SPECIAL,"");
    }

    public static String extractEng(String removeWhitespace) {
        return removeWhitespace.replaceAll(REG_ENG, "");
    }

    public static String extractNum(String removeWhitespace) {
        return removeWhitespace.replaceAll(REG_NUM, "");
    }

    public static String removeWhiteSpace(String removeTagHtml) {
        return removeTagHtml.replaceAll(REG_REMOVE_WHITESPACE, "");
    }

    public static String removeTag(String html){
        return html.replaceAll(REG_REMOVE_TAG, "");
    }
}
