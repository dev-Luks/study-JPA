package com.example.myoceanproject.controller.login.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/*")
public class CategoryController {

    //카테고리 페이지
    @GetMapping("/index")
    public String Category(){
        return "app/category/category";
    }


}
