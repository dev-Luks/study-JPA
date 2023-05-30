package com.example.myoceanproject.controller.search;


import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.service.search.SearchService;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-search/*")
@Slf4j
public class SearchRestController {
    private final SearchService searchService;


    //    /list?page=0&size="+globalThis.size+"&search="+keyword
    @PostMapping("/list")
    public Slice<CommunityPostDTO> showList(@PageableDefault(size = 10, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable, String search, String[] categoriesList, HttpSession session){
        ArrayList<CommunityCategory> categories = new ArrayList<>();
        if(categoriesList != null){
            //화면에서는 String, 문자열만 넘어오기 때문에 우리가 원하는 ENUM타입으로 변경해주는 작업
            for(String category : categoriesList){
                if(category.equals("EXERCISE")){ // 사용자가 요청한 값 중 운동이 있다면
                    categories.add(CommunityCategory.EXERCISE);
                }
                if(category.equals("COOK")){
                    categories.add(CommunityCategory.COOK);
                }
                if(category.equals("MOVIE")){
                    categories.add(CommunityCategory.MOVIE);
                }
                if(category.equals("BOOK")){
                    categories.add(CommunityCategory.BOOK);
                }
                if(category.equals("COUNSELING")){
                    categories.add(CommunityCategory.COUNSELING);
                }
            }
        }

        return searchService.showList(pageable,search,categories,session);
    }


}