package com.example.myoceanproject.controller.law;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/law/*")
public class LawController {
    // 이용약관 페이지
    @GetMapping("/termsofService")
    public String lawTermsofService(){
        return "app/law/termsofservice";
    }

    // 개인정보 처리방침 페이지
    @GetMapping("/privacyPolicy")
    public String lawPrivacyPolicy(){
        return "app/law/privacypolicy";
    }

    // 위치기반 서비스 이용약관 페이지
    @GetMapping("/locationBasedService")
    public String lawlocationBasedService(){ return "app/law/locationBasedService"; }


}
