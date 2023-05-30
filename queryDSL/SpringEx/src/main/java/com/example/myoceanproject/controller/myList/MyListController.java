package com.example.myoceanproject.controller.myList;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.DiaryDTO;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.repository.MyPostRepository;
import com.example.myoceanproject.service.DiaryService;
import com.example.myoceanproject.service.myPost.MyPostService;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

@Slf4j
@Controller
@RequestMapping("/myList/*")
public class MyListController {

    @Autowired
    private MyPostService myPostService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private MyPostRepository myPostRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    // 내가 쓴 일기 페이지
    @GetMapping("/myDiary")
    public String myDiary(){ return "app/myList/myDiary"; }

    // 내가 보낸 교환일기 페이지
    @GetMapping("/myExchangeDiary")
    public String myExchangeDiary(){ return "app/myList/myExchangeDiary"; }

    @GetMapping(value="/myDiaryDetail")
    public String myDiaryDetail(Model model,Long diaryId){
        model.addAttribute("diaryDTO",diaryService.findDetailDiary(diaryId));
        return "app/myList/myDiaryDetail";
    }

    // 카테고리 ( 합치는 부분!)
    @GetMapping("/myListCategory")
    public String myListCategory(){
        return "app/myList/myList";
    }

    // 내가 쓴 게시글 (전체)
    @GetMapping("/myListTotal")
    public String myListTotal(){ return "app/myList/myListTotal"; }

    //  책 이야기
    @GetMapping("/myListBook")
    public String myListBook(){ return "app/myList/myListBookBoard"; }

    //  요리 이야기
    @GetMapping("/myListCook")
    public String myListCook(){ return "app/myList/myListCookBoard"; }

    //  고민상담
    @GetMapping("/myListCounseling")
    public String myListCounseling(){ return "app/myList/myListCounselingBoard"; }

    //  운동 이야기
    @GetMapping("/myListExercise")
    public String myListExercise(){ return "app/myList/myListExerciseBoard"; }

    // 영화이야기
    @GetMapping("/myListMovie")
    public String myListMovie(){ return "app/myList/myListMovieBoard"; }

    // 자유게시판
//    @GetMapping("/myListFree")
//    public String myListFree(Model model,Criteria criteria, HttpServletRequest request){

//        HttpSession session=request.getSession();
//
//        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);
//
//        Page<CommunityPostDTO> postDTOPage= myPostService.showCounseling(pageable, CommunityCategory.FREEBOARD, criteria,(Long)session.getAttribute("userId"));
//        int endPage = (int)(Math.ceil((postDTOPage.getNumber()+1) / (double)10)) * 10;
//        if(postDTOPage.getTotalPages() < endPage) {
//            endPage = postDTOPage.getTotalPages() == 0 ? 1 : postDTOPage.getTotalPages();
//        }
//        log.info(endPage + "end");
//
//        log.info("pagenation:"+model.getAttribute("pagination"));
//        log.info("criteria:"+criteria.getPage());
//
//        model.addAttribute("freeboards", postDTOPage.getContent());
//        model.addAttribute("pagination", postDTOPage);
//        model.addAttribute("pageable", pageable);
//        model.addAttribute("criteria", criteria);
//        model.addAttribute("endPage", endPage);

//        return "app/myList/myListFreeBoard";
//    }

    // 게시글 없는 페이지 표시할때!
    @GetMapping("/myListNocontents")
    public String myListNoContents(){
        return "app/myList/myListNoContents";
    }

}
