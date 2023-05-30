package com.example.myoceanproject.controller.bulletin_board;


import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/group/*")
public class BulletinboardRestController {

    private final GroupService groupService;

    // 모임 전체 게시글 페이징처리
    // 검색어(keyword)가 없으면 전체 페이징 처리만 진행
    @GetMapping("/group/{page}/{keyword}")
    public GroupDTO getGroupList(@PathVariable int page, @PathVariable(required = false) String keyword){
        // 특수문자 삽입 시 텍스트가 깨지지 않도록 조정
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        //criteria에 페이지와 키워드 추가
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);

        // 한 페이지당 12개의 게시글로 하며, 첫 페이지가 아닐 경우 criteria에서 가져온 페이지에서 1을 뺀 페이지를 현재 페이지로 한다.
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 12);

        // showGroup메소드로 페이지와 criteria정보 전달
        Page<GroupDTO> groupDTOPage = groupService.showGroup(pageable, criteria);


        GroupDTO groupDTO = new GroupDTO();
        
        // groupDTO 내에 내용과 전체 페이지 전달
        // EndPage에는 totalPage를 넣고, >> 버튼 클릭 시 10페이지씩 이동처리
        groupDTO.setGroupList(groupDTOPage.getContent());
        groupDTO.setEndPage(groupDTOPage.getTotalPages());

        groupDTOPage.getContent().stream().map(GroupDTO::toString).forEach(log::info);

        return groupDTO;
    }


}
