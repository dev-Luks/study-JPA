package com.example.myoceanproject.controller.communtiy;

import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.entity.CommunityReply;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepository;
import com.example.myoceanproject.service.community.CommunityPostService;
import com.example.myoceanproject.service.community.CommunityReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reply/*")
public class CommunityReplyRestController {

    private final CommunityReplyService communityReplyService;
    private final CommunityPostService communityPostService;
    private final UserRepository userRepository;
    private final CommunityPostRepository communityPostRepository;
    private final CommunityReplyRepository communityReplyRepository;


    //  댓글 목록 출력
    @GetMapping("/list/{bno}")
    public List<CommunityReplyDTO> getReplyList(@PathVariable("bno") Long communityPostId){
        // 커뮤니티 ID를 통해 해당 게시글에 속한 모든 댓글을 리스트 형식으로 불러옴
        List<CommunityReplyDTO> communityReplyDTOList = communityReplyService.findAllByCommunityId(communityPostId);
        return communityReplyDTOList;
    }


    //  댓글 등록
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> add(@RequestBody CommunityReplyDTO communityReplyDTO, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        
        // 모델 객체에 로그인한 계정 정보 저장
        model.addAttribute("userId", userId);

        // RequestBody로 담아온 communityReplyDTO 값을 엔티티화하여 CommunityReply 객체 communityReply에 저장
        CommunityReply communityReply = communityReplyDTO.toEntity();
        // communityReply에 유저 정보 저장
        communityReply.setUser(userRepository.findById(userId).get());
        // communityReply에 communityPost정보 저장
        communityReply.setCommunityPost(communityPostRepository.findById(communityReplyDTO.getCommunityPostId()).get());
        // 댓글 정보 저장
        communityReplyService.add(communityReplyDTO);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/delete-reply/{communityReplyId}")
    public String remove(@PathVariable("communityReplyId") Long communityReplyId){
        // 댓글 ID를 통해 해당 댓글 삭제
        communityReplyService.delete(communityReplyId);
        return "delete success";
    }
}
