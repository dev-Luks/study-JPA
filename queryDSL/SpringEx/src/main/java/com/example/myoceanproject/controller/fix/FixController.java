package com.example.myoceanproject.controller.fix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fix/*")
public class FixController {

    // 헤더 페이지
    @GetMapping("/header")
    public String header(){
        return "app/fix/header";
    }

    @GetMapping("/headerModify")
    public String headerModify(){
        return "app/fix/headerModify";
    }

    // 풋터 페이지
    @GetMapping("/footer")
    public String footer(){
        return "app/fix/footer";
    }
}
