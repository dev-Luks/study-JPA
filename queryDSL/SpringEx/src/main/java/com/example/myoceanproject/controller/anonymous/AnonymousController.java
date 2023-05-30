package com.example.myoceanproject.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anonymous/*")
public class AnonymousController {

    // 고민상담 페이지
    @GetMapping("/index")
    public String anonymous(){
        return "app/anonymous/anonymous";
    }
}
