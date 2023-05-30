package com.example.expert.repository.board;

import com.example.expert.entity.board.Like;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class LikeDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    조회
    public Optional<Like> findById(Long id){
        String query = "select l from Like l join fetch l.board b where l.id = :id";
        return Optional.ofNullable(
                entityManager
                        .createQuery(query, Like.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }
}
