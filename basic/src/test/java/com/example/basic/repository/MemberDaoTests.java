package com.example.basic.repository;

import com.example.basic.domain.entity.Member;
import com.example.basic.type.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional // 필드 안에 있는 모든 메소드에 트랜잭션이 각각 생긴다.
@Rollback(false)
public class MemberDaoTests {
    @Autowired
    private MemberDAO memberDAO;

//    @Test
//    public void saveTest(){
//        for (int i=0; i<100; i++){
//            Member member = new Member();
//            member.setMemberName("user" + (i + 1));
//            member.setMemberEmail("user" + (i + 1) +"@gmail.com");
//            member.setMemberPassword("" + i);
//            member.setMemberType(MemberType.MEMBER);
//            member.setMemberAge(i + 1);
//
//            memberDAO.save(member);
//        }
//    }

//    @Test
//    public void setMemberAgeTest(){
//        Member member = new Member();
//        member.setMemberName("한동석");
//        member.setMemberEmail("tedhan1204@gmail.com");
//        member.setMemberPassword("1234");
//        member.setMemberType(MemberType.MEMBER);
//        member.setMemberAge(20);
//
//        memberDAO.save(member);
//        member.setMemberAge(99);
//    }

    @Test
    public void deleteTest(){
//        Member member = new Member();
//        member.setMemberName("한동석");
//        member.setMemberEmail("tedhan1204@gmail.com");
//        member.setMemberPassword("1234");
//        member.setMemberType(MemberType.MEMBER);
//        member.setMemberAge(20);
//
//        memberDAO.save(member);
        memberDAO.findById(4L).ifPresent(memberDAO::delete);
    }

    @Test
    public void findByIdTest(){
        Optional<Member> optionalMember = memberDAO.findById(10L);
//        Member member = optionalMember.orElse(new Member());
//        Member member = optionalMember.orElseGet(Member::new);

//        if(optionalMember.isPresent()){
//            optionalMember.get();
//        }

//        optionalMember.ifPresent(member -> assertThat(member.getMemberName()).isEqualTo("한동석"));
    }

    @Test
    public void findAllTest(){
        memberDAO.findAll().stream().map(Member::getMemberName).forEach(log::info);
    }

    @Test
    public void findAllWithPagingTest(){
        memberDAO.findAllWithPaging(11, 10).stream().map(Member::getMemberName).forEach(log::info);
    }

    @Test
    public void findByMemberNameTest(){
//        memberDAO.findByMemberName("user99").stream().map(Member::getMemberName).forEach(log::info);
        assertThat(memberDAO.findByMemberName("user99").stream().map(Member::getMemberAge).map(String::valueOf).collect(Collectors.joining())).isEqualTo("99");
    }

    @Test
    public void deleteByMemberAgeGreaterThanEqualTest(){
        memberDAO.deleteByMemberAgeGreaterThanEqual(90);
    }

    @Test
    public void updateByMemberAgeLessThanEqualTest(){
        memberDAO.findAll();
        memberDAO.updateByMemberAgeLessThanEqual(20);
        memberDAO.findAll().stream().map(Member::getMemberType).map(Enum::toString).forEach(log::info);
    }
}






















