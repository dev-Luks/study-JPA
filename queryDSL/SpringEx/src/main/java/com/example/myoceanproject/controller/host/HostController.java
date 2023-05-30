package com.example.myoceanproject.controller.host;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/host/*")
public class HostController {

    private final GroupService groupService;

    // 게시글 목록
    @GetMapping("/group-list")
    public String list(Model model){
        // group에 포함된 모든 게시글들의 정보를 model객체에 담아줌
        model.addAttribute("groupDTOs", groupService.show());
        return "app/bulletin_board/bulletin_board";
    }


    // 소모임 작성 페이지
    @GetMapping("/index")
    public String host(Model model){
        model.addAttribute("groupDTO", new GroupDTO());
        return "app/host/host";
    }

    // 게시글 상세보기
    @GetMapping("/read")
    public String read(Long groupId, Model model, Model model2, Model model3, Model model4, Model model5, Model model6, HttpServletRequest request) throws UnsupportedEncodingException{
        HttpSession session= request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        // 해당 게시글의 groupDTO 정보
        model.addAttribute("groupDTO", groupService.find(groupId));
        // 모임 게시글 중 최신 5개 정보
        model2.addAttribute("groupTop5DTOs", groupService.findTop5BygroupId(groupId));
        // 모임 스케줄 정보
        model3.addAttribute("groupScheduleDTO", groupService.findAllByGroupId(groupId));
        // 현재 시간 정보
        model4.addAttribute("localDateTime", LocalDateTime.now());
        // 해당 게시글의 모임에 속한 회원인지 확인 정보
        if(userId != null){
            model5.addAttribute("groupUserCheck", groupService.findGroupUser(userId, groupId));
        } else{
            model5 = null;
        }
        // 해당 모임에 속한 멤버의 수
        model6.addAttribute("groupJoinMember", groupService.countGroupMember(groupId));

        // 모든 정보를 모임 상세 게시글로 가져감
        return "app/bulletin_board/bulletin_board_detail";
    }

    // 게시글 수정하기
    @GetMapping("update")
    public String update(Long groupId, Model model){
        // 그룹 ID를 통해 group 정보를 모델객체에 저장
        model.addAttribute("groupDTO", groupService.find(groupId));
        // 해당 정보를 게시글 작성(수정) 화면으로 이동
        return "app/host/host";
    }

    // 게시글 삭제하기
    @GetMapping("/deleteGroup")
    public RedirectView delete(Long groupId){
        // 그룹ID를 통해 해당 그룹을 삭제
        groupService.delete(groupId);
        // 삭제 후 모임 전체 페이지로 이동
        return new RedirectView("/host/group-list");
    }
}