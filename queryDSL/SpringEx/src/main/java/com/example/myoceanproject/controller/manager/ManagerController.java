package com.example.myoceanproject.controller.manager;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.service.UserService;
import com.example.myoceanproject.service.community.CommunityPostService;
import com.example.myoceanproject.service.community.CommunityReplyService;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/manager/*")
public class ManagerController {

    // 고민상담 게시글 관리
    @GetMapping("/counselingBoard")
    public String counselingBoard() {

        return "app/manager/admin_counseling_board";
    }

    // 고민상담 댓글 관리
    @GetMapping("/counselingReply")
    public String counselingReply() {

        return "app/manager/admin_counseling_board_reply";
    }

    // 자유게시판 게시글 관리
    @GetMapping("/freeBoard")
    public String freeBoard() {
        return "app/manager/admin_free_board";
    }

    // 자유게시판 댓글 관리
    @GetMapping("/freeReply")
    public String freeReply() {

        return "app/manager/admin_free_board_reply";
    }

    //  모임 개설 신청 관리
    @GetMapping("/groupOpenRequest")
    public String groupopenRequest() {
        return "app/manager/admin_group_open_request";
    }

    //  대쉬보드
    @GetMapping("/index")
    public String layOut() {
        return "app/manager/admin_dashboard";
    }

    //  배너 관리
    @GetMapping("/pageBanner")
    public String pageBanner() {
        return "app/manager/admin_page_banner";
    }

    //  퀘스트 관리
    @GetMapping("/questList")
    public String questList() {
        return "app/manager/admin_quest_list";
    }

    //  문의사항 목록
    @GetMapping("/questions")
    public String questions() {
        return "app/manager/admin_questions";
    }

    //  회원 목록
    @GetMapping("/userList")
    public String userList() {

//        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);
//
//        Page<UserDTO> userDTOPage= userService.showAllUser(pageable, criteria);
//        int endPage = (int)(Math.ceil(userDTOPage.getNumber()+1 / (double)10)) * 10;
//        if(userDTOPage.getTotalPages() < endPage){
//            endPage = userDTOPage.getTotalPages() == 0 ? 1 : userDTOPage.getTotalPages();
//        }
//        log.info(endPage + "end");
//
//        model.addAttribute("users", userDTOPage.getContent());
//        model.addAttribute("pagination", userDTOPage);
//        model.addAttribute("pageable", pageable);
//        model.addAttribute("criteria", criteria);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("active", UserAccountStatus.ACTIVE);
//        model.addAttribute("banned", UserAccountStatus.BANNED);
        return "app/manager/admin_user_list";
    }

}
