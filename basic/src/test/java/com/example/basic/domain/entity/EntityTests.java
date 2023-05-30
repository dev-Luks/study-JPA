package com.example.basic.domain.entity;

import com.example.basic.type.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class EntityTests {

    @PersistenceContext
    private EntityManager entityManager;

//    트랜잭션 영역에서만 DML을 사용할 수 있다.
//    단위 테스트에서는 자동으로 Rollback되기 때문에 false로 설정한다.
    @Test @Transactional @Rollback(false)
    public void entityTest(){
        Member memberA = new Member();
        Member memberB = new Member();

        memberA.setMemberName("한동석");
        memberA.setMemberEmail("tedhan1204@gmail.com");
        memberA.setMemberPassword("1234");
        memberA.setMemberType(MemberType.MEMBER);
        memberA.setMemberAge(20);

        memberB.setMemberName("홍길동");
        memberB.setMemberEmail("hgd1234@gmail.com");
        memberB.setMemberPassword("9999");
        memberB.setMemberType(MemberType.ADMIN);
        memberB.setMemberAge(50);

//        Persistence Context에 등록, 1차 캐시에 저장
        entityManager.persist(memberA);
        entityManager.persist(memberB);

//        쓰기 지연 저장소에 있었던 SQL을 DB로 전송, commit() 전에 자동으로 실행
//        1차 캐시는 그대로 유지
        entityManager.flush();

//        1차 캐시 전체 삭제
        entityManager.clear();

//        1차 캐시에 조회할 엔티티가 있다면, SQL 조회 없이 1차 캐시에서 가져온다(성능 최적화).
        Member foundMember1 = entityManager.find(Member.class, 1L);

//        entityManager.flush();
//        entityManager.clear();

        Member foundMember2 = entityManager.find(Member.class, 1L);

//        1차 캐시에 등록된 엔티티가 있다면, 동일성이 보장된다.
        assertThat(foundMember1).isEqualTo(foundMember2);

//        변경 감지(더티 체킹), 딱 하나의 컬럼만 수정이 가능하다.
        foundMember1.setMemberAge(99);

        foundMember1 = entityManager.find(Member.class, 1L);
        assertThat(foundMember1.getMemberAge()).isEqualTo(99);

//        entityManager.flush();
//        entityManager.clear();
        
//        영속 상태 : 1차 캐시에 등록된 상태
//        준영속 상태 : detached instance이며, detached()를 사용하여 1차 캐시로부터 분리된 상태
//        비영속 상태 : 1차 캐시에 등록되지 않은 상태
//        삭제 상태 : remove()를 사용하여 1차 캐시로부터 삭제된 상태

//        영속 상태인 객체일 경우에만 삭제가 가능하다.
        entityManager.remove(foundMember2);
    }
}















