package com.example.myoceanproject.controller.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/myOcean/*")
public class IndexController {
    @GetMapping("/index")
    public String main(){
        return "app/Main/main";
    }
}
