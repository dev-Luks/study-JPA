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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest(){
        Member member1 = Member.builder()
                .memberName("한동석")
                .memberAge(20)
                .memberEmail("tedhan1204@gmail.com")
                .memberPassword("1234")
                .memberType(MemberType.MEMBER)
                .build();

        Member member2 = Member.builder()
                .memberName("홍길동")
                .memberAge(19)
                .memberEmail("hgd1234@gmail.com")
                .memberPassword("4321")
                .memberType(MemberType.MEMBER)
                .build();

        Member member3 = Member.builder()
                .memberName("이순신")
                .memberAge(18)
                .memberEmail("lss4321@gmail.com")
                .memberPassword("7890")
                .memberType(MemberType.MEMBER)
                .build();

        Member member4 = Member.builder()
                .memberName("장보고")
                .memberAge(33)
                .memberEmail("jbg7777@gmail.com")
                .memberPassword("7979")
                .memberType(MemberType.ADMIN)
                .build();

        List<Member> members = new ArrayList<>(Arrays.asList(member1, member2, member3, member4));

        memberRepository.saveAll(members);
    }

    @Test
    public void findByIdTest(){
        memberRepository.findById(101L).ifPresent(member -> log.info(member.getMemberName()));
    }

    @Test
    public void findAllTest(){
        assertThat(memberRepository.findAll()).hasSize(4);
    }

    @Test
    public void findByMemberNameTest(){
        memberRepository.findByMemberName("이순신").stream().map(Member::toString).forEach(log::info);
    }

    @Test
    public void findByMemberNameContainingTest(){
        assertThat(memberRepository.findByMemberNameContaining("동")).hasSize(2);
    }

    @Test
    public void findByMemberNameStartingWithTest(){
        assertThat(memberRepository.findByMemberNameStartingWith("홍")).hasSize(1);
    }

    @Test
    public void findByMemberNameEndingWithTest(){
        assertThat(memberRepository.findByMemberNameEndingWith("고")).hasSize(1);
    }

    @Test
    public void findTop2ByMemberAgeGreaterThanEqualOrderByMemberAgeDescTest(){
        memberRepository
                .findTop2ByMemberAgeGreaterThanEqualOrderByMemberAgeDesc(20)
                .stream().map(Member::toString).forEach(log::info);
    }

    @Test
    public void existsByMemberAgeTest(){
        assertThat(memberRepository.existsByMemberAge(18)).isTrue();
    }

    @Test
    public void deleteByMemberAgeGreaterThanEqualTest(){
        memberRepository.deleteByMemberAgeGreaterThanEqual(20);
    }
}










