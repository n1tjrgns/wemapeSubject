package com.wemape.subject.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public static ParserDto makeParserWithFilter(String crawlingData, String type) {
        if ("H".equals(type)) {
            String hParseString = Parser.removeTag(crawlingData);
            return getParserDto(hParseString);
        }

        String tParseString = Parser.removeSpecial(crawlingData);

        return getParserDto(tParseString);
    }

    private static ParserDto getParserDto(String parseString) {
        String removeWhiteSpace = Parser.removeWhiteSpace(parseString);
        String numberStr = Parser.extractNum(removeWhiteSpace);
        String engStr = Parser.extractEng(removeWhiteSpace);
        log.info("영어만 출력 {}", engStr);
        log.info("숫자만 출력 {}", numberStr);

        return ParserDto.make(numberStr, engStr);
    }
}
