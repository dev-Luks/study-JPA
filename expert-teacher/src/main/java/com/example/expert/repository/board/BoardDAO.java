package com.example.expert.repository.board;

import com.example.expert.entity.board.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    추가
    public Board save(Board board){
        entityManager.persist(board);
        return board;
    }

//    조회
    public Optional<Board> findById(Long id){
        String query = "select b from Board b join fetch b.likes where b.id = :id";
        return Optional.ofNullable(
                entityManager
                        .createQuery(query, Board.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

//    전체 조회
    public List<Board> findAll(){
//        N:1관계에서 "1"쪽을 조회 시, join을 하면, "N"쪽의 개수만큼 결과 행이 중복되어 조회된다.
//        이 때 중복된 행을 없애기 위해서 distinct를 SELECT절에 사용한다.
        String query = "select distinct b from Board b join fetch b.likes";
        return entityManager.createQuery(query, Board.class).getResultList();
    }

//    삭제
    public void delete(Board board){
        entityManager.remove(board);
    }
}




















