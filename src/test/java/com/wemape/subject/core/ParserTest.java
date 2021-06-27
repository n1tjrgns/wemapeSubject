package com.wemape.subject.core;

import com.wemape.subject.dto.ParseResponse;
import com.wemape.subject.dto.ParseResponseFactory;
import com.wemape.subject.service.HParserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ParserTest {

    @Autowired
    HParserService hParserService;

    @Test
    void html태그_제거() {
        String html = "<html>" +
                "<head>" +
                "</head" +
                "<body>" +
                "hi" +
                "alert('ㅎㅇ');" +
                "</body>" +
                "</html>";

        String removeTag = Parser.removeTag(html);

        assertEquals(removeTag, "hialert('ㅎㅇ');");
    }

    @Test
    void 특수문자_제거() {
        String special = "<html>123!@?=#$%^&*()_+hihi;";

        String removeSpecial = Parser.removeSpecial(special);

        assertEquals(removeSpecial, "html123hihi");
    }

    @Test
    void 공백_전부_제거() {
        String whiteSpace = "             \n   white";

        String removeWhiteSpace = Parser.removeWhiteSpace(whiteSpace);

        assertEquals(removeWhiteSpace, "white");
    }

    @Test
    void 숫자와_영어중_숫자만_추출() {
        String num = "123good";

        String extractNum = Parser.extractNum(num);

        assertEquals(extractNum, "123");
    }

    @Test
    void 숫자와_영어중_영어만_추출() {
        String eng = "123good";

        String extractEng = Parser.extractEng(eng);

        assertEquals(extractEng, "good");
    }

    //Html 제거, text 추출 후 -> 공백 제거 후 -> 영어 또는 숫자 추출

    @Test
    void HTML태그_제거_몫_나머지_확인(){
        int count = 10;
        String doc = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/html\"> \n" +
                " <head> \n" +
                "  <title>Hello</title> \n" +
                " </head>\n" +
                " <body>\n" +
                "  <h1>ㅎㅎ</h1> \n" +
                "  <h1>234234452454235</h1> \n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">   \n" +
                "  <div class=\"container\"> \n" +
                "   <form role=\"form\" action=\"/search\" method=\"post\" novalidate> \n" +
                "    <div class=\"form-group\"> <label for=\"url\">URL</label> \n" +
                "     <input type=\"text\" name=\"url\" class=\"form-control\" id=\"url\" value=\"\"> \n" +
                "    </div> \n" +
                "    <div class=\"form-group\"> <label for=\"type\">Type</label> <select name=\"type\" id=\"type\" class=\"form-control\"> <option value=\"H\" selected>HTML 태그 제외</option> <option value=\"T\">Text 전체</option> </select> \n" +
                "    </div> \n" +
                "    <div class=\"form-group\"> <label for=\"count\">출력 단위 묶음</label> \n" +
                "     <input type=\"text\" name=\"count\" class=\"form-control\" id=\"count\" value=\"\"> \n" +
                "    </div> <button type=\"submit\" class=\"btn btn-primary\">출력</button> \n" +
                "   </form> \n" +
                "   <div class=\"form-group\"> \n" +
                "    <p> <label for=\"result\">출력</label><br> <textarea id=\"result\" cols=\"150\" rows=\"30\"></textarea> </p> \n" +
                "    <p> <label for=\"quotient\">몫 : </label> <span id=\"quotient\"></span> </p> \n" +
                "    <p> <label for=\"remainder\">나머지 : </label> <span id=\"remainder\"></span> </p> \n" +
                "   </div> \n" +
                "  </div>  \n" +
                " </body>\n" +
                "</html>";

        ParserDto parserDto = Parser.makeParserWithFilter(doc, "H");
        String mixString = StringConvertUtil.getMixString(parserDto.getNumberToString(), parserDto.getEngToString());
        ParseResponse parseResponse = ParseResponseFactory.of(mixString, count);

        assertEquals(parseResponse.getQuotient(),3);
        assertEquals(parseResponse.getRemainder(),5);
    }


    @Test
    void Text전체_로직_성공(){
        int count = 10;
        String doc = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/html\"> \n" +
                " <head> \n" +
                "  <title>Hello</title> \n" +
                " </head>\n" +
                " <body>\n" +
                "  <h1>ㅎㅎ</h1> \n" +
                "  <h1>234234452454235</h1> \n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">   \n" +
                "  <div class=\"container\"> \n" +
                "   <form role=\"form\" action=\"/search\" method=\"post\" novalidate> \n" +
                "    <div class=\"form-group\"> <label for=\"url\">URL</label> \n" +
                "     <input type=\"text\" name=\"url\" class=\"form-control\" id=\"url\" value=\"\"> \n" +
                "    </div> \n" +
                "    <div class=\"form-group\"> <label for=\"type\">Type</label> <select name=\"type\" id=\"type\" class=\"form-control\"> <option value=\"H\" selected>HTML 태그 제외</option> <option value=\"T\">Text 전체</option> </select> \n" +
                "    </div> \n" +
                "    <div class=\"form-group\"> <label for=\"count\">출력 단위 묶음</label> \n" +
                "     <input type=\"text\" name=\"count\" class=\"form-control\" id=\"count\" value=\"\"> \n" +
                "    </div> <button type=\"submit\" class=\"btn btn-primary\">출력</button> \n" +
                "   </form> \n" +
                "   <div class=\"form-group\"> \n" +
                "    <p> <label for=\"result\">출력</label><br> <textarea id=\"result\" cols=\"150\" rows=\"30\"></textarea> </p> \n" +
                "    <p> <label for=\"quotient\">몫 : </label> <span id=\"quotient\"></span> </p> \n" +
                "    <p> <label for=\"remainder\">나머지 : </label> <span id=\"remainder\"></span> </p> \n" +
                "   </div> \n" +
                "  </div>  \n" +
                " </body>\n" +
                "</html>";

        ParserDto parserDto = Parser.makeParserWithFilter(doc, "T");
        String mixString = StringConvertUtil.getMixString(parserDto.getNumberToString(), parserDto.getEngToString());
        ParseResponse parseResponse = ParseResponseFactory.of(mixString, count);

        assertEquals(parseResponse.getQuotient(),73);
        assertEquals(parseResponse.getRemainder(),4);
    }
}