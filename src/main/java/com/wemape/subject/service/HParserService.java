package com.wemape.subject.service;

import com.wemape.subject.core.Parser;
import com.wemape.subject.core.ParserDto;
import com.wemape.subject.core.StringConvertUtil;
import com.wemape.subject.dto.ParseResponse;
import com.wemape.subject.dto.ParseResponseFactory;
import com.wemape.subject.infra.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HParserService {
    public ParseResponse parser(String url, String type, int count) {
        String crawlingData = JsoupUtil.getCrawlingData(url);

        ParserDto parserDto = Parser.makeParserWithFilter(crawlingData, type);

        String mixedString = StringConvertUtil.getMixString(parserDto.getNumberToString(), parserDto.getEngToString());

        return ParseResponseFactory.of(mixedString, count);
    }
}
