package com.example.intermediate.repository.user;

import com.example.intermediate.entity.user.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberDAO {
    @PersistenceContext
    private EntityManager entityManager;
//    추가
    public Member save(Member member){
        entityManager.persist(member);
        return member;
    }

//    조회
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

//    전체 조회
    public List<Member> findAll(){
        String query = "select m from Member m";
        return entityManager.createQuery(query, Member.class).getResultList();
    }

//    삭제
    public void delete(Member member){
        entityManager.remove(member);
    }

}
