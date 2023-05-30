package com.example.intermediate.entity.user;

import com.example.intermediate.repository.user.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class MemberTests {
    @Autowired
    private MemberDAO memberDAO;

    @Test
    public void saveTest(){
        Member member = new Member();
        member.setName("한동석");
        member.setUserId("hds1234");
        member.setSocialSecurityNumber("001204-3456789");
        member.setAddress("경기도 남양주");
        member.setPassword("1234");

        memberDAO.save(member);
    }

    @Test
    public void findByIdTest(){
        memberDAO.findById(3L).map(User::toString).ifPresent(log::info);
    }

    @Test
    public void findAll() {
        assertThat(memberDAO.findAll().size()).isEqualTo(0);
    }

    @Test
    public void updateTest(){
        memberDAO.findById(3L).ifPresent(member -> member.setName("홍길동"));
    }

    @Test
    public void deleteTest(){
        memberDAO.findById(3L).ifPresent(memberDAO::delete);
    }
}
