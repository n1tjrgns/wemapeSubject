package com.wemape.subject.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class ParserDto {

    private String numberToString;
    private String engToString;

    public static ParserDto make(String numberToString, String engToString) {
        return ParserDto.builder()
                .numberToString(numberToString)
                .engToString(engToString)
                .build();
    }
}
