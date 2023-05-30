package com.example.expert.repository.inquire;

import com.example.expert.entity.inquire.Answer;
import com.example.expert.entity.inquire.Question;
import com.example.expert.entity.inquire.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class InquireRepositoryTests {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void questionSaveTest(){
        Question question = Question.builder()
                .questionTitle("문의 합니다.")
                .questionContents("비밀번호 변경이 되지 않습니다.")
                .build();
        questionRepository.save(question);
    }

    @Test
    public void answerSaveTest(){
//        Answer answer = Answer.builder().answerContents("비밀번호 변경은 한 달에 한 번만 가능합니다.").build();
        Answer answer = Answer.builder().answerContents("혹시 한 달이 지났음에도 불구하고 비밀번호 변경이 되지 않으면 고객센터로 문의부탁드립니다!").build();
        questionRepository.findById(347L).ifPresent(answer::setQuestion);
        answerRepository.save(answer);
    }

//    @Test
//    public void findByIdWithAnswerCount(){
//        log.info(questionRepository.findByIdWithAnswerCount(347L).toString());
//    }

    @Test
    public void findByIdWithAnswersTest(){
//        log.info(questionRepository.findByIdWithAnswers(347L).toString());
        questionRepository.findByIdWithAnswers(347L)
                .stream().map(Question::toDTO)
                .map(QuestionDTO::toString)
                .forEach(log::info);
    }
}
























