package com.wemape.subject.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import static com.wemape.subject.dto.RequestForm.REG_NUM;
import static com.wemape.subject.dto.RequestForm.REG_URL;

@Component
public class RequestFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestForm requestForm = (RequestForm) target;

        boolean urlMatches = Pattern.matches(REG_URL, requestForm.getUrl());
        boolean numMatches = Pattern.matches(REG_NUM, requestForm.getCount());
        if (!urlMatches){
            errors.rejectValue("url", "wrong.url", "url 형식을 확인해주세요.");
        }

        if (!numMatches){
            errors.rejectValue("count", "wrong.count","묶음 수를 확인해주세요.");
        }

        if (Integer.parseInt(requestForm.getCount()) == 0) {
            errors.rejectValue("count", "wrong.value","1보다 큰 수만 가능합니다.");
        }
    }
}
