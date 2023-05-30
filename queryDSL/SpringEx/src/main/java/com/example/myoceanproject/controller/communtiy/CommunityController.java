package com.example.myoceanproject.controller.communtiy;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.service.community.CommunityPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/community/*")
public class CommunityController {

    private final CommunityPostService communityPostService;
    private final CommunityReplyRepositoryImpl communityReplyRepositoryImpl;
    private final CommunityLikeRepositoryImpl communityLikeRepositoryImpl;

    // 커뮤니티 메인 페이지로 이동
    @GetMapping("/index")
    public String community(){
        return "app/community/community";
    }

    // 커뮤니티 상세 페이지로 이동
    @GetMapping("/read")
    public String communityDetail(Long communityPostId, Model model, HttpServletRequest request){
        CommunityPostDTO communityPostDTO = new CommunityPostDTO();
        // communityPostId를 통해 해당하는 정보를 communityPostDTO로 받아옴
        communityPostDTO = communityPostService.find(communityPostId);
        // 세션에 담긴 아이디값을 받아옴
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        // 만약 아이디값이 있다면(로그인한 상태라면) 각 커뮤니티 게시글에 로그인한 유저가 좋아요를 눌렀는지 체크
        if(userId != null){
            communityPostDTO.setCheckLike(communityLikeRepositoryImpl.findByCommunityPostAndUser(userId,communityPostDTO.getCommunityPostId()));
        }
        // 각 게시글에 댓글이 몇 개 달렸는지 체크
        communityPostDTO.setCommunityReplyCount(communityReplyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        // model객체에 communityPostDTO 값을 담는다.
        model.addAttribute("communityPostDTO", communityPostDTO);
        // 게시글 세부페이지에서 사용하기 위해 return
        return "app/community/detail";
    }

    // 게시글 수정하기
    @GetMapping("update")
    public String update(Long communityPostId, Model model){
        // model 객체에 communityPostId를 통해 communityPostDTO에 communityPost값을 받아옴
        model.addAttribute("communityPostDTO", communityPostService.find(communityPostId));
        // 게시글 등록 페이지로 model객체를 받아옴
        return "app/community/community_register";
    }


    // 커뮤니티 글쓰기 페이지
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("communityPostDTO", new CommunityPostDTO());
        return "app/community/community_register";
    }

    // 게시글 삭제하기
    @GetMapping("/deleteBoard")
    @Transactional
    public RedirectView delete(Long communityPostId){
        // community 아이디를 통해 작성 게시글 정보를 불러와 삭제
        communityPostService.remove(communityPostId);
        // 삭제 후 커뮤니티 메인 페이지로 이동
        return new RedirectView("/community/index");
    }
    
    /* 모임 목록 */
    @GetMapping("/bulletin")
    // 모임 메인 페이지로 이동
    public String bulletin(){ return "app/bulletin_board/bulletin_board"; }

}
