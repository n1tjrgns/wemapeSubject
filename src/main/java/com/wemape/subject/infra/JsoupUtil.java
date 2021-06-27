package com.wemape.subject.infra;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class JsoupUtil {

    public static String getCrawlingData(String url) {
        try {
            return String.valueOf(Jsoup.connect(url).get());
        } catch(IOException e) {
            log.info("url을 불러오는 도중 오류 발생 : {}", e.getMessage());
            return "";
        }
    }
}
