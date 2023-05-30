package com.example.myoceanproject.controller.myGroup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myGroup/*")
@Slf4j
public class MyGroupController {

    // 내가 참여한 모임 페이지
    @GetMapping("/joinGroup")
    public String joinGroup(){ return "app/myGroup/myJoinGroup"; }

    // 내가 오픈한 모임 페이지
    @GetMapping("/openGroup")
    public String openGroup(){ return "app/myGroup/myOpenGroup"; }
}
