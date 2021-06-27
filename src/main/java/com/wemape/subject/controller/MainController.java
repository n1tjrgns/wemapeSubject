package com.wemape.subject.controller;

import com.wemape.subject.dto.ParseResponse;
import com.wemape.subject.dto.RequestForm;
import com.wemape.subject.dto.RequestFormValidator;
import com.wemape.subject.service.HParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final HParserService hParserService;
    private final RequestFormValidator requestFormValidator;

    @InitBinder("requestForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(requestFormValidator);
    }

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute(new RequestForm());
        return "index";
    }

    @PostMapping("/search")
    public String search(@Valid RequestForm requestForm, Errors errors, @RequestParam("type") String type, Model model){
        if (errors.hasErrors()) {
            log.info("requestForm 형식 에러 {}", errors.getObjectName());
            return "index";
        }

        ParseResponse parseResponse = hParserService.parser(requestForm.getUrl(), type, Integer.parseInt(requestForm.getCount()));

        model.addAttribute("result", parseResponse.getResult());
        model.addAttribute("quotient", parseResponse.getQuotient());
        model.addAttribute("remainder", parseResponse.getRemainder());

        return "index";
    }
}
