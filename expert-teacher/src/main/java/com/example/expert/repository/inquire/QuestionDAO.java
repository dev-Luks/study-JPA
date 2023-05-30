package com.example.expert.repository.inquire;

import com.example.expert.entity.inquire.Answer;
import com.example.expert.entity.inquire.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    엔티티 추가
    public Question save(Question question){
        entityManager.persist(question);
        return question;
    }

//    참조 엔티티 추가
    public Answer save(Answer TBLAnswer){
        entityManager.persist(TBLAnswer);
        return TBLAnswer;
    }

//    조회
    public Optional<Question> findById(Long id){
        return Optional.ofNullable(entityManager.find(Question.class, id));
//        String query = "select q from Question q join fetch q.answer where q.id = :id";
//        return Optional.ofNullable(
//            entityManager
//                    .createQuery(query, Question.class)
//                    .setParameter("id", id)
//                    .getSingleResult());
    }


    //    참조 엔티티 조회
    public Optional<Answer> findAnswerById(Long id){
        String query = "select a from Answer a where id = :id";
        return Optional.ofNullable(entityManager.createQuery(query, Answer.class).setParameter("id", id).getSingleResult());
    }

//    전체 조회
    public List<Question> findAll(){
        String query = "select q from Question q join fetch q.answer";
        return entityManager.createQuery(query, Question.class).getResultList();
    }

//    삭제
    public void delete(Question question){
        entityManager.remove(question);
    }

//    참조 엔티티 삭제
    public void delete(Answer answer){
        entityManager.remove(answer);
    }
}















