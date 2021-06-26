package com.wemape.subject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RequestForm {
    //url 정규표현식
    public static final String REG_URL = "^((http|https)://)([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    //양의 정수만
    public static final String REG_NUM = "^\\d*$";

    @NotBlank
    @Pattern(regexp = REG_URL)
    private String url;

    @NotEmpty
    @Pattern(regexp = REG_NUM)
    private String count;
}
