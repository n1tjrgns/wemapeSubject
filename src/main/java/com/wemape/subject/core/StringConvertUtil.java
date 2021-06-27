package com.wemape.subject.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class StringConvertUtil {

    public static String chaining(String hParseString, int count, int mok) {
        StringBuffer sb = new StringBuffer(hParseString);

        int index = count;
        for (int i = 0; i <mok; i++) {
            sb.insert(index, "\r");
            index = index + count +1;
        }

        return sb.toString();
    }

    //숫자 영어 섞기
    public static String getMixString(String numberStr, String engStr) {

        String[] num = splitWithSort(numberStr);
        String[] eng = splitWithSort(engStr);

        return getMix(num, eng);
    }

    private static String [] splitWithSort(String str) {
        String[] result = str.split("");
        Arrays.sort(result);

        log.info("오름차순 출력 {}", Arrays.toString(result));
        return result;
    }

    private static String getMix(String[] num, String[] eng) {

        int min = Math.min(num.length, eng.length);
        int max = Math.max(num.length, eng.length);
        String[] maxString = num.length == max ? num : eng;

        StringBuffer sb = getQuotient(num, eng, min);

        sb = getRemainder(sb, min, max, maxString);
        return sb.toString();
    }

    private static StringBuffer getQuotient(String[] num, String[] eng, int min) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        while (index < min) {
            sb.append(eng[index]);
            sb.append(num[index]);
            index++;
        }

        return sb;
    }

    private static StringBuffer getRemainder(StringBuffer sb, int startIdx, int endIdx, String [] str) {
        for (int idx = startIdx; idx < endIdx; idx++) {
            sb.append(str[idx]);
        }

        return sb;
    }
}