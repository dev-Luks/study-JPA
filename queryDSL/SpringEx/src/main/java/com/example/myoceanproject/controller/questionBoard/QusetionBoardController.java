package com.example.myoceanproject.controller.questionBoard;

import com.example.myoceanproject.aspect.annotation.AskAlarm;
import com.example.myoceanproject.domain.AskDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.entity.Ask;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.ask.AskRepository;
import com.example.myoceanproject.service.AskService;
import com.example.myoceanproject.type.AskCategory;
import com.example.myoceanproject.type.AskStatus;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.example.myoceanproject.entity.QAsk.ask;

@Slf4j
@Controller
@RequestMapping("/questionBoard/*")
public class QusetionBoardController {

    @Autowired
    private AskService askService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private UserRepository userRepository;

    // 자주 묻는 질문 메인 페이지
    @GetMapping("/index")
    public String questionBoard(){ return "app/questionBoard/questionBoard"; }

    //  이용 안내
    @GetMapping("/questionUsingInfo")
    public String questionUsingInfo(){ return "app/questionBoard/questionUsingInfo"; }

    //  회원 정보
    @GetMapping("/questionUserInfo")
    public String questionUserInfo(){ return "app/questionBoard/questionUserInfo"; }

    //  결제/환불
    @GetMapping("/questionPointInfo")
    public String questionPointInfo(){ return "app/questionBoard/questionPointInfo"; }

    //  퀘스트
    @GetMapping("/questionQuestInfo")
    public String questionQuestInfo(){ return "app/questionBoard/questionQuestInfo"; }

    // 자주 묻는 질문 중 나의 질문 페이지
    @GetMapping("myQuestion")
    public String myQuestionBoard(){ return "app/questionBoard/myQuestion"; }

    @GetMapping("usersQuestion")
    public String usersQuestion(){ return "app/questionBoard/usersQuestion"; }
    // 문의 사항 작성 페이지
    @GetMapping("myQuestionWrite")
    public String myQuestionWrite(){ return "app/questionBoard/myQuestionWrite"; }

    @RequestMapping("/myQuestionWriteOk")
    @ResponseBody
    @Transactional
    @Modifying
    public String myQuestionWriteOk(@RequestBody ObjectNode objectNode){

        AskDTO askDTO=new AskDTO();
        User user1=new User();
        AskCategory askCategory=null;

        String askTitle=objectNode.get("askTitle").asText();
        String askContent=objectNode.get("askContent").asText();
        Long askUser=objectNode.get("askUser").asLong();

        if(askTitle.contains("이용")){
            askCategory=AskCategory.USINGINFO;
        }else if(askTitle.contains("회원")){
            askCategory=AskCategory.USINGINFO;
        }else if(askTitle.contains("포인트")){
            askCategory=AskCategory.POINTINFO;
        } else if(askTitle.contains("퀘스트")){
            askCategory=AskCategory.QUESTINFO;
        }else{;}

        askDTO.setAskTitle(askTitle);
        askDTO.setAskContent(askContent);
        askDTO.setAskStatus("답변대기");
        askDTO.setAskCategory(askCategory.toString());
        Ask ask=askDTO.toEntity();

        Optional<User> user = userRepository.findById(askUser);

        ask.changeUser(user.get());
        log.info("ask:"+ask);
        askRepository.save(ask);

        return "success";
    }
    // 유저 문의 사항
    @GetMapping("managerAnswer")
    public String managerAnswer(HttpServletRequest request,Model model){
        Long searchAskId=Long.valueOf(request.getParameter("askId"));

        Ask asks=jpaQueryFactory.selectFrom(ask).where(ask.askId.eq(searchAskId)).fetchOne();
        model.addAttribute("managerAnswers",asks);
        return "app/questionBoard/managerAnswer";
    }

    @RequestMapping("/usersQuestionWriteOk")
    @ResponseBody
    @Transactional
    @Modifying
    @AskAlarm
    public String usersQuestionWriteOk(@RequestBody ObjectNode objectNode){

        Long askId=objectNode.get("askId").asLong();
        String askContent=objectNode.get("askContent").asText();

        Ask askUser=jpaQueryFactory.selectFrom(ask).where(ask.askId.eq(askId)).fetchOne();
        askUser.updateStatus(AskStatus.COMPLETE);
        askUser.updateAnswer(askContent);

        log.info("ask:"+ask);

        return "success";
    }
}
