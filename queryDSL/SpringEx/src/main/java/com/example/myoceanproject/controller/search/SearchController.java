//  손호현, 통합검색 SearchController
package com.example.myoceanproject.controller.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/search/*")
public class SearchController {

    // 통합 검색 페이지
    @GetMapping("/totalSearch")
    public String totalSearch(){
        return "app/Search/Search";
    }

    // 통합 검색이 없을때 페이지
    @GetMapping("/noTotalSearch")
    public String noTotalSearch(){
        return "app/Search/NoSearch";
    }



    @GetMapping("/search")
    public String searchList(String keyword, Model model){

        model.addAttribute("keyword",keyword);
        return "app/Search/SearchResult";
    }
}
