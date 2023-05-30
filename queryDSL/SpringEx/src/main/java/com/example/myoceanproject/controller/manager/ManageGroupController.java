package com.example.myoceanproject.controller.manager;


import com.example.myoceanproject.aspect.annotation.GroupAlarm;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.service.GroupService;
import com.example.myoceanproject.service.UserService;
import com.example.myoceanproject.type.GroupStatus;
import com.example.myoceanproject.type.UserAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/group/*")
public class ManageGroupController {
//    브라우저에서 JSON 타입으로 데이터를 전송하고 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 리턴한다.
//    consumes : 전달받은 데이터의 타입
//    produces : 콜백함수로 결과를 전달할 때의 타입
//    @RequestBody : 전달받은 데이터를 알맞는 매개변수로 주입
//    ResponseEntity : 서버의 상태코드, 응답 메세지 등을 담을 수 있는 타입

    private final GroupService groupService;

    @GetMapping("/{page}/{keyword}/{status}")
    public GroupDTO list(@PathVariable String status, @PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setKeyword(decodeKeyword);
        criteria.setPage(page);
        criteria.setStatus(status);
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);
        GroupStatus groupStatus = null;

        switch(criteria.getStatus()) {
            case "waiting":
                groupStatus = GroupStatus.WAITING;
                break;
            case "disapproved":
                groupStatus = GroupStatus.DISAPPROVED;
                break;
            case "approved":
                groupStatus = GroupStatus.APPROVED;
                break;
        };

        Page<GroupDTO> groupDTOPage= groupService.findAllByStatus(pageable, groupStatus, criteria);

        log.info(groupDTOPage.getTotalPages() + "end");

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupList(groupDTOPage.getContent());
        groupDTO.setEndPage(groupDTOPage.getTotalPages());


        return groupDTO;
    }
    @GetMapping("/{page}/{keyword}")
    public GroupDTO list(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setKeyword(decodeKeyword);
        criteria.setPage(page);
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<GroupDTO> groupDTOPage= groupService.findAllManage(pageable, criteria);

        log.info(groupDTOPage.getTotalPages() + "end");

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupList(groupDTOPage.getContent());
        groupDTO.setEndPage(groupDTOPage.getTotalPages());


        return groupDTO;
    }
    @GroupAlarm
    @PatchMapping(value = "/{status}/{groupId}")
    public String disApprovedGroup(@PathVariable Long groupId, @PathVariable String status){
        if(status.equals("disapprove")){
            groupService.modifyStatus(groupId, "승인거절");
        }else{
            groupService.modifyStatus(groupId, "승인완료");
        }
        return "update success";
    }



}
