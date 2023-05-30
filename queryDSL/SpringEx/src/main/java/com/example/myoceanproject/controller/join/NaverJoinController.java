package com.example.myoceanproject.controller.join;

import com.example.myoceanproject.service.oAuth.NaverOAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/naver-join/*")
@RequiredArgsConstructor
public class NaverJoinController {
    private final NaverOAuthService naverOAuthService;

    @GetMapping("/join")
    public String naverCallback(Model model, @RequestParam(value = "code") String authCode, HttpSession session) throws Exception{

        String token = naverOAuthService.getNaverAccessToken(authCode);
        session.setAttribute("token", token);
        int exist = naverOAuthService.naverProfile(token);

        if(exist==0){
            session.invalidate();
            return "redirect:/main/index?join=1";
        }
        else{
            return "redirect:/login/index?login=1";
        }
    }



}
