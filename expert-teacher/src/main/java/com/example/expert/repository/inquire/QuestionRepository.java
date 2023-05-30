package com.example.expert.repository.inquire;

import com.example.expert.entity.inquire.Question;
import com.example.expert.entity.inquire.QuestionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

//    @Query("select new com.example.expert.entity.inquire.QuestionDTO(q.id, q.questionTitle, q.questionContents, q.createdDate, q.updatedDate, size(q.answers)) from Question q")
//    public List<QuestionDTO> findByIdWithAnswerCount(Long id);

    @Query("select distinct q from Question q inner join q.answers")
    public List<Question> findByIdWithAnswers(Long id);
}
