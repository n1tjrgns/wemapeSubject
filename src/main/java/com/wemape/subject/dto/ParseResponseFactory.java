package com.wemape.subject.dto;

import com.wemape.subject.core.StringConvertUtil;

public class ParseResponseFactory {

    public static ParseResponse of(String parseString, int count) {
        int parseLength = parseString.length();
        int mok = parseLength / count;
        int nmg = parseLength % count;

        //묶음단위로 묶기
        if (mok != 0){
            parseString = StringConvertUtil.chaining(parseString, count, mok);
        }

        return ParseResponse.builder()
                .result(parseString)
                .quotient(mok)
                .remainder(nmg)
                .build();
    }
}
