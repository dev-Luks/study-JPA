package com.example.myoceanproject.controller.bulletin_board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bulletinBoard/*")
public class BulletinboardController {

    //모임 목록 페이지로 이동
    @GetMapping("/index")
    public String bulletinBoard(){
        return "app/bulletin_board/bulletin_board";
    }

}
