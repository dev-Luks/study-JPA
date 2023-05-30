package com.example.myoceanproject.controller.search;


import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search-host/*")
@Slf4j
@RequiredArgsConstructor
public class SearchHostRestController {
        private final SearchService searchService;
    @GetMapping("/{search}")
    public Page<GroupDTO> getList(@PageableDefault(size = 12, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("search") String search){
        return searchService.showList(pageable,search);
    }
}
