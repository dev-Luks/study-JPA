package com.example.myoceanproject.controller.manager;


import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.service.community.CommunityReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reply/*")
public class ManageReplyController {
    @Autowired
    private CommunityReplyService replyService;
//    브라우저에서 JSON 타입으로 데이터를 전송하고 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 리턴한다.
//    consumes : 전달받은 데이터의 타입
//    produces : 콜백함수로 결과를 전달할 때의 타입
//    @RequestBody : 전달받은 데이터를 알맞는 매개변수로 주입
//    ResponseEntity : 서버의 상태코드, 응답 메세지 등을 담을 수 있는 타입

    @GetMapping("/free/{page}/{keyword}")
    public CommunityReplyDTO getFreeBoardReply(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityReplyDTO> replyDTOPage= replyService.showReply(pageable, criteria);

        log.info(replyDTOPage.getTotalPages() + "end");

        CommunityReplyDTO replyDTO = new CommunityReplyDTO();

        replyDTO.setReplyList(replyDTOPage.getContent());
        replyDTO.setEndPage(replyDTOPage.getTotalPages());

        replyDTOPage.getContent().stream().map(CommunityReplyDTO::toString).forEach(log::info);

        return replyDTO;
    }
    @GetMapping("/counseling/{page}/{keyword}")
    public CommunityReplyDTO getCounselingReply(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);

        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<CommunityReplyDTO> replyDTOPage= replyService.showReplyByCounseling(pageable, criteria);

        log.info(replyDTOPage.getTotalPages() + "end");

        CommunityReplyDTO replyDTO = new CommunityReplyDTO();

        replyDTO.setReplyList(replyDTOPage.getContent());
        replyDTO.setEndPage(replyDTOPage.getTotalPages());

        replyDTOPage.getContent().stream().map(CommunityReplyDTO::toString).forEach(log::info);

        return replyDTO;
    }

    @DeleteMapping("/{replyNumber}")
    public String deleteReply(@PathVariable Long replyNumber){
        replyService.remove(replyNumber);
        return "delete success";
    }

}
