package com.example.expert.repository.inquire;

import com.example.expert.entity.inquire.Answer;
import com.example.expert.entity.inquire.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
@Rollback(false)
public class InquireTests {

    @Autowired private QuestionDAO questionDAO;
    @Autowired private AnswerDAO answerDAO;

    @Test
    public void saveTest(){
//        "단방향에서는
//          INSERT 실행 시, @JoinColumn으로 설정된 객체로만 FK를 추가할 수 있다.
//          @JoinColumn이 설정되지 않은 연관객체로는 FK를 추가할 수 없다.
//        "하지만, 양방향에서는
//          @JoinColumn을 사용하지 않아도 mappedBy로 FK를 설정한다.
//          mappedBy를 생략하면 모든 테이블에 FK가 생긴다.
//          RDB에서 설계할 때 N쪽에 FK를 두기 때문에
//          FK를 필드로 가지고 있는 엔티티가 연관관계의 주인이 되어야 한다.

//        ※ 문제 발생
//        mappedBy를 question으로 설정했기 때문에,
//        question_id는 Question엔티티에서 관리하게 된다.
//        따라서 Answer엔티티에 question_id를 추가하고 싶다면,
//        answer에 question을 넣어주어야 한다.
//        Answer answer = new Answer();
//        Question question = new Question();

//        answer.setAnswerContents("답변 내용1");
//        questionDAO.save(answer);
//
//        question.setQuestionTitle("문의 제목1");
//        question.setQuestionContents("문의 내용1");
//        question.setAnswer(answer);
//
//        questionDAO.save(question);

//        ※ 해결
//        Answer answer = new Answer();
//        Question question = new Question();
//
//        question.setQuestionTitle("문의 제목1");
//        question.setQuestionContents("문의 내용1");
//        questionDAO.save(question);
//
//        answer.setAnswerContents("답변 내용1");
//        answer.setQuestion(question);
//
//        questionDAO.save(answer);
    }

    @Test
    public void findByIdTest(){
        questionDAO.findById(73L).map(Question::toString).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        assertThat(questionDAO.findAll()).hasSize(1);
    }

    @Test
    public void deleteTest(){
//        단위 테스트에서는 Rollback(true)가 default이므로
//        Rollback(false)를 설정한 뒤
//        Transaction의 rollbackFor를 사용해도 반영되지 않는다.
        questionDAO.findAnswerById(86L).ifPresent(questionDAO::delete);
        questionDAO.findById(87L).ifPresent(questionDAO::delete);
    }

    @Test
    public void updateTest(){
        questionDAO.findAnswerById(86L).ifPresent(answer -> answer.getQuestion().setQuestionContents("수정된 내용"));
    }
}



















