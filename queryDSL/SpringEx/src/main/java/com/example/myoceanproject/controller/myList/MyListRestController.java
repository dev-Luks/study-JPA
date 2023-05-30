package com.example.myoceanproject.controller.myList;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.Diary;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.DiaryRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.DiaryService;
import com.example.myoceanproject.service.community.CommunityPostService;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.DiaryCategory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myList/*")
public class MyListRestController {

    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityPostService PostService;

    @GetMapping(value={"/diary/{page}","/diary/{page}/{dateData}"})
    public DiaryDTO getCalendarList(HttpServletRequest request,@PathVariable("page") int page,@PathVariable(value="dateData",required=false) List<String> dateData){
        log.info("/diary/{page} in");
        Page<DiaryDTO> diaryDTOpage=null;
        log.info("dateData:"+dateData);

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        String decodeKeyword="";
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        if(dateData!=null) {
            diaryDTOpage = diaryService.showSelectDiarys(pageable, dateData, userId, criteria);
        }else{
            diaryDTOpage = diaryService.showFirstDiarys(pageable,userId, criteria);
        }

        log.info(diaryDTOpage.getTotalPages() + "end");

        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryList(diaryDTOpage.getContent());
        diaryDTO.setEndPage(diaryDTOpage.getTotalPages());

        diaryDTOpage.getContent().stream().map(DiaryDTO::toString).forEach(log::info);

        return diaryDTO;
    }

    // 게시글 작성 후 커뮤니티 페이지로 이동
    @Transactional
    @Modifying
    @PostMapping(value="/index", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public String diaryWriting(@RequestBody DiaryDTO diaryDTO, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        return diaryService.determineSaveOrExDiary(userId,diaryDTO);

    }

    @GetMapping("/myListBook/{page}/{keyword}")
    public CommunityPostDTO getBookBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showCounseling(pageable, CommunityCategory.BOOK,criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);

        return postDTO;
    }

    @GetMapping("/myListCook/{page}/{keyword}")
    public CommunityPostDTO getCookBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showCounseling(pageable, CommunityCategory.COOK,criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);

        return postDTO;
    }

    @GetMapping("/myListExercise/{page}/{keyword}")
    public CommunityPostDTO getExerciseBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showCounseling(pageable, CommunityCategory.EXERCISE,criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);

        return postDTO;
    }

    @GetMapping("/myListMovie/{page}/{keyword}")
    public CommunityPostDTO getMovieBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showCounseling(pageable, CommunityCategory.MOVIE,criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);

        return postDTO;
    }

    @GetMapping("/myListCounseling/{page}/{keyword}")
    public CommunityPostDTO getCounselingBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showCounseling(pageable, CommunityCategory.COUNSELING,criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);

        return postDTO;
    }

    @GetMapping("/myListTotal/{page}/{keyword}")
    public CommunityPostDTO getListTotalBoard(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityPostDTO> postDTOPage= PostService.showPost(pageable, criteria);

        log.info(postDTOPage.getTotalPages() + "end");

        CommunityPostDTO postDTO = new CommunityPostDTO();

        postDTO.setPostList(postDTOPage.getContent());
        postDTO.setEndPage(postDTOPage.getTotalPages());

        postDTOPage.getContent().stream().map(CommunityPostDTO::toString).forEach(log::info);
//        myExchangeDiary
        return postDTO;
    }

    @GetMapping("/myExchangeDiary/{page}/{keyword}")
    public DiaryDTO getExBoard(@PathVariable int page, @PathVariable(required = false) String keyword,HttpServletRequest request){

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        String decodeKeyword="";
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<DiaryDTO> diaryDTOpage= diaryService.showExchangeDiary(pageable,userId, criteria);

        log.info(diaryDTOpage.getTotalPages() + "end");

        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryList(diaryDTOpage.getContent());
        diaryDTO.setEndPage(diaryDTOpage.getTotalPages());

        diaryDTOpage.getContent().stream().map(DiaryDTO::toString).forEach(log::info);

        return diaryDTO;
    }
}