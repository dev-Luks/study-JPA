package com.example.myoceanproject.controller.questionBoard;

import com.example.myoceanproject.domain.AskDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.repository.MyAskRepository;
import com.example.myoceanproject.service.AskService;
import com.example.myoceanproject.type.AskCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questionBoard/*")
public class QuestionBoardRestController {

    private final AskService askService;
    private final MyAskRepository myAskRepository;

    @GetMapping("/usingInfo/{page}/{keyword}")
    public AskDTO getUsingInfo(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showQuestion(pageable, AskCategory.USINGINFO ,criteria,userId);

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }
    @GetMapping("/userInfo/{page}/{keyword}")
    public AskDTO getUserInfo(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showQuestion(pageable, AskCategory.ACCOUNTINFO ,criteria,userId);

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }
    @GetMapping("/pointInfo/{page}/{keyword}")
    public AskDTO getPointInfo(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showQuestion(pageable, AskCategory.POINTINFO ,criteria,userId);

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }
    @GetMapping("/questInfo/{page}/{keyword}")
    public AskDTO getQuestInfo(@PathVariable int page, @PathVariable(required = false) String keyword, HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showQuestion(pageable, AskCategory.QUESTINFO ,criteria,userId);

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }

    @GetMapping("/questionBoard/{page}/{keyword}")
    public AskDTO getAllQuestion(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showAllQuestion(pageable, criteria);

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }

    @GetMapping("/myQuestion/{page}/{keyword}")
    public AskDTO getMyQuestion(@PathVariable int page, @PathVariable(required = false) String keyword,HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showAllMyAsk(pageable, criteria,(Long)session.getAttribute("userId"));

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }

    @GetMapping("/usersQuestion/{page}/{keyword}")
    public AskDTO getUsersQuestion(@PathVariable int page, @PathVariable(required = false) String keyword,HttpServletRequest request){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        HttpSession session=request.getSession();
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<AskDTO> askDTOPage= askService.showAllUserAsk(pageable, criteria,(Long)session.getAttribute("userId"));

        log.info(askDTOPage.getTotalPages()+"end");

        AskDTO askDTO = new AskDTO();

        askDTO.setAskList(askDTOPage.getContent());
        askDTO.setEndPage(askDTOPage.getTotalPages());

        askDTOPage.getContent().stream().map(AskDTO::toString).forEach(log::info);

        return askDTO;
    }
}
