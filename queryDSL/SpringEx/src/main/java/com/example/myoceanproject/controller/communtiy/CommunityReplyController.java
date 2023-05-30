package com.example.myoceanproject.controller.communtiy;

import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.UserRepositoryImpl;
import com.example.myoceanproject.service.community.CommunityPostService;
import com.example.myoceanproject.service.community.CommunityReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply/*")
public class CommunityReplyController {

    private final CommunityPostService communityPostService;
    private final CommunityReplyService communityReplyService;
    private final UserRepositoryImpl userRepositoryImpl;

//  댓글 목록 출력
    @GetMapping("/index")
    public String getReply(Long communityPostId, Model model, Model model2, HttpServletRequest request){
        // communityPostId를 통해 해당 ID에 속한 정보를 communityPostDTO를 받아 model 객체에 담아줌
        model.addAttribute("communityPostDTO", communityPostService.find(communityPostId));

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        model2.addAttribute("userDTO", userRepositoryImpl.findUserById(userId));

        // 각 게시글의 댓글 페이지로 communityPostDTO 정보 전달
        return "app/community/community_comment";
    }

}
