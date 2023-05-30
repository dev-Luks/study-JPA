package com.example.myoceanproject.controller.communtiy;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.service.community.CommunityLikeService;
import com.example.myoceanproject.service.community.CommunityPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like/*")
public class CommunityLikeRestController {
    private final CommunityLikeService communityLikeService;
    private final CommunityPostRepository communityPostRepository;


//  좋아요 눌렀는 지 확인
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/checkLike/{communityPostId}")
    public Boolean checkLike(@PathVariable("communityPostId") Long communityPostId, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        // 게시글 작성자가 아닌 사용자의 아이디
        Long userId = (Long) session.getAttribute("userId");
        // 유저 ID 값과 커뮤니티 게시글 ID값을 통해 해당 게시글을 로그인한 유저가 좋아요 눌렀는지 체크
        Boolean checkLike = communityLikeService.checkLike(userId, communityPostId);
        // Boolean값을 리턴
        return checkLike;
    }


//  좋아요 버튼 클릭(좋아요 추가)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/addLike/{communityPostId}")
    public ResponseEntity<String> add(@PathVariable("communityPostId") Long communityPostId, HttpServletRequest request) throws UnsupportedEncodingException{
        HttpSession session = request.getSession();
        //게시글 작성자가 아닌 사용자의 아이디
        Long userId = (Long) session.getAttribute("userId");
        
        // community 아이디값을 통해 해당하는 CommunityPost를 불러오고, communityPost 변수에 값을 넣어줌
        CommunityPost communityPost = communityPostRepository.findById(communityPostId).get();
        // 클릭한 communityPost 게시글 좋아요 추가(업데이트)
        communityPost.updateLikePlusCount();

        // Like 테이블에 저장
        communityLikeService.addLike(userId, communityPostId);

        return new ResponseEntity<>(new String("like success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

//  좋아요 버튼 클릭(좋아요 취소(삭제))
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/minusLike/{communityPostId}")
    public ResponseEntity<String> minus(@PathVariable("communityPostId") Long communityPostId, HttpServletRequest request) throws UnsupportedEncodingException{
        HttpSession session = request.getSession();
        //게시글 작성자가 아닌 사용자의 아이디
        Long userId = (Long) session.getAttribute("userId");

        // community 아이디값을 통해 해당하는 CommunityPost를 불러오고, communityPost 변수에 값을 넣어줌
        CommunityPost communityPost = communityPostRepository.findById(communityPostId).get();
        // 클릭한 communityPost 게시글 좋아요 삭제(업데이트)
        communityPost.updateLikeMinusCount();

        // Like 테이블에서 해당 내용 삭제
        communityLikeService.minusLike(userId, communityPostId);

        return new ResponseEntity<>(new String("like success".getBytes(), "UTF-8"), HttpStatus.OK);
    }
}
