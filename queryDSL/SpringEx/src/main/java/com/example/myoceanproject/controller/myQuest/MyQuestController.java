package com.example.myoceanproject.controller.myQuest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/myQuest/*")
public class MyQuestController {
    // 완료한 퀘스트 페이지
    @GetMapping("/completeQuest")
    public String completeQuest(){
        return "app/myQuest/completeQuest";
    }

    // 획득한 벳지 페이지
    @GetMapping("/myBadge")
    public String myBadge(){


        return "app/myQuest/myBadge";
    }

    // 오늘의 퀘스트 페이지
    @GetMapping("/todayQuest")
    public String todayQuest(){
        return "app/myQuest/todayQuest";
    }

}
