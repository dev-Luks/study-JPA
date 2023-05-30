package com.example.basic.repository;

import com.example.basic.domain.entity.Member;
import com.example.basic.type.MemberType;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class MemberDAO {
//    1. application.yml파일에 작성된 Connection 정보를 통해 EntityManageFactory가 생성된다.
//    2. EntityManageFactory를 통해 EntityManager객체가 생성된다.
    @PersistenceContext // EntityManager를 통해서 생성된 Entity객체가 등록되는 영역
    private EntityManager entityManager; // EntityManageFactory를 통해 생성되며, Connection 객체를 통해 SQL문을 제작해준다.

//    등록
    public Member save(Member member){
        entityManager.persist(member);
        entityManager.flush();
        return member;
    }

//    PK로 조회
    public Optional<Member> findById(Long id){
//        Optional은 NULL이 아니기 때문에 NPE를 방지할 수 있으며,
//        필드로 들어간 엔티티의 NULL 검사를 편하게 할 수 있는 API이다.
//        단, Optional은 리턴 타입에서만 사용하는 것을 권장한다.
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

//    삭제
    public void delete(Member member) {
        entityManager.remove(member);
    }

//    JPQL - 객체 지향 쿼리 언어
//    엔티티 객체를 대상으로 쿼리를 작성해야 한다.
//    JPQL은 SQL로 변환된다.
//    키워드는 대소문자 구분이 없다.
//    별칭(as) 필수

//    TypedQuery : 리턴 타입을 정확히 알 때, 전달한 타입으로 리턴된다.
//    Query : 리턴 타입이 정확하지 않을 때, Object로 리턴된다.

//    전체 조회
    public List<Member> findAll(){
//        entityManager.createQuery("select m from Member m");
        String query = "select m from Member m"; // Inject language or reference -> JPA QL

//        Query result = entityManager.createQuery(query);

        TypedQuery<Member> result = entityManager.createQuery(query, Member.class);
        return result.getResultList();
    }

//    전체 조회 페이징 처리
    public List<Member> findAllWithPaging(int startRow, int rowCount){
        String query = "select m from Member m order by m.id desc";
        return entityManager.createQuery(query, Member.class)
                .setFirstResult(startRow - 1)
                .setMaxResults(rowCount)
                .getResultList();
    }

//    특정 회원 조회
    public List<Member> findByMemberName(String memberName){
//        String query = "select m from Member m where m.memberName = ?1";
        String query = "select m from Member m where m.memberName = :memberName";
        TypedQuery<Member> result = entityManager.createQuery(query, Member.class);
        result.setParameter("memberName", memberName);
        return result.getResultList();
    }

//    특정 회원 삭제
    public void deleteByMemberAgeGreaterThanEqual(int memberAge){
        String query = "delete from Member m where m.memberAge >= :memberAge";
        entityManager.createQuery(query).setParameter("memberAge", memberAge).executeUpdate();
    }

//    특정 회원 수정
//    벌크 연산: 여러 값을 수정하는 쿼리를 벌크 연산이라고 하며, 1차 캐시에 상관없이 즉시 SQL문이 실행된다.
    public void updateByMemberAgeLessThanEqual(int memberAge){
        String query = "update Member m set m.memberType = :memberType where m.memberAge <= :memberAge";
        entityManager.createQuery(query)
                .setParameter("memberType", MemberType.MEMBER)
                .setParameter("memberAge", memberAge)
                .executeUpdate();
        entityManager.clear();
    }
}













