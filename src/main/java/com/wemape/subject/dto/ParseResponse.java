package com.wemape.subject.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParseResponse {

    String result;
    int quotient; //몫
    int remainder; //나머지
    String exception;

    @Builder
    public ParseResponse(String result, int quotient, int remainder) {
        this.result = result;
        this.quotient = quotient;
        this.remainder = remainder;
    }
}
