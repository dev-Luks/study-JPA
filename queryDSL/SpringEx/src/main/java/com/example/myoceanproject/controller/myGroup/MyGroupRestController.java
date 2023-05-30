package com.example.myoceanproject.controller.myGroup;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.service.MyGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myGroup/*")
public class MyGroupRestController {

    private final MyGroupService myGroupService;

    @GetMapping("/myJoinGroup/{page}/{keyword}")
    public GroupDTO getMyJoinGroup(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<GroupDTO> groupDTOPage= myGroupService.showJoinGroup(pageable,userId,criteria);

        log.info(groupDTOPage.getTotalPages()+"end");

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setGroupList(groupDTOPage.getContent());
        groupDTO.setEndPage(groupDTOPage.getTotalPages());

        groupDTOPage.getContent().stream().map(GroupDTO::toString).forEach(log::info);

        return groupDTO;
    }

    @GetMapping("/myOpenGroup/{page}/{keyword}")
    public GroupDTO getMyOpenGroup(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<GroupDTO> groupDTOPage= myGroupService.showOpenGroup(pageable,userId, criteria);

        log.info(groupDTOPage.getTotalPages()+"end");

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setGroupList(groupDTOPage.getContent());
        groupDTO.setEndPage(groupDTOPage.getTotalPages());

        groupDTOPage.getContent().stream().map(GroupDTO::toString).forEach(log::info);

        return groupDTO;
    }
}
