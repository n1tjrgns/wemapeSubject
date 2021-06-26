package com.wemape.subject.service;

import com.wemape.subject.core.Parser;
import com.wemape.subject.dto.ParseResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class HParserService {

    public ParseResponse start(String url, String type, int count) {
        ParseResponse result=null;
        try {
            String crawlingData = String.valueOf(Jsoup.connect(url).get());
            if ("H".equals(type)){
                String hParseString = hFilterHtml(crawlingData);
                log.info("HTML 태그 제외 교차 출력 {}", hParseString);

                result = calculate(hParseString, count);//나머지, 몫 계산
            }else if ("T".equals(type)){
                String tParseString = tFilterHtml(crawlingData);
                log.info("text 전체 교차 출력 {}", tParseString);

                result = calculate(tParseString, count);
            }
        }catch(IOException e) {
            log.info("url을 불러오는 도중 오류 발생 : {}", e.getMessage());
        }
        return result;
    }


    //몫, 나머지 계산
    public ParseResponse calculate(String hParseString, int count) {
        int parseLength = hParseString.length();
        int mok = parseLength / count;
        int nmg = parseLength % count;

        //묶음단위로 묶기
        if (mok != 0){
            hParseString = chaining(hParseString, count, mok);
        }

        return ParseResponse.builder()
                .result(hParseString)
                .quotient(mok)
                .remainder(nmg)
                .build();
    }

    private String chaining(String hParseString, int count, int mok) {
        StringBuffer sb = new StringBuffer(hParseString);

        int index = count;
        for (int i = 0; i <mok; i++) {
            sb.insert(index, "\r");
            index = index + count +1;
        }

        return sb.toString();
    }

    //html 태그 없애기
    public String hFilterHtml(String crawlingData) {
        String removeTagHtml = Parser.removeTag(crawlingData); ////HTML 태그 제외
        return commonRemoveFilter(removeTagHtml);
    }

    //특수 문자 없애기
    public String tFilterHtml(String crawlingData) {
        String removeSpecialWord = Parser.removeSpecial(crawlingData);
        return commonRemoveFilter(removeSpecialWord);
    }

    //공백제거 및 영어, 숫자 출력
    private String commonRemoveFilter(String filterWord) {
        String removeWhiteSpace = Parser.removeWhiteSpace(filterWord);
        String numberStr = Parser.extractNum(removeWhiteSpace);
        String engStr = Parser.extractEng(removeWhiteSpace);
        log.info("HTML 태그 제외(영어) 출력 = " + engStr);
        log.info("HTML 태그 제외(숫자) 출력 = " + numberStr);

        StringBuffer sb = getMixString(numberStr, engStr);
        return sb.toString();
    }

    //숫자 영어 섞기
    private StringBuffer getMixString(String numberStr, String engStr) {
        String[] num = numberStr.split("");
        String[] eng = engStr.split("");
        Arrays.sort(num);
        Arrays.sort(eng);

        log.info("HTML 태그 제외(영어) 오름차순 출력 {}", Arrays.toString(eng));
        log.info("HTML 태그 제외(숫사) 오름차순 출력 {}", Arrays.toString(num));

        int index=0;
        int numLength = num.length;
        int engLength = eng.length;

        StringBuffer sb = new StringBuffer();
        int min = Math.min(numLength, engLength);
        while (index < min) {
            sb.append(eng[index]);
            sb.append(num[index]);

            index++;
        }
        if (numLength > engLength){
            for (int i = min; i < numLength; i++) {
                sb.append(num[min++]);
            }

        }else if(numLength < engLength){
            for (int j = min; j < engLength; j++) {
                sb.append(eng[min++]);
            }
        }
        return sb;
    }




}
