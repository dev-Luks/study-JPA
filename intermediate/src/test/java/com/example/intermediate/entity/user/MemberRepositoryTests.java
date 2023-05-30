package com.example.intermediate.entity.user;

import com.example.intermediate.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

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
                .name("한동석")
                .userId("hds1234")
                .password("1234")
                .socialSecurityNumber("001204-1234567")
                .address("경기도")
                .build();

        Member member2 = Member.builder()
                .name("이순신")
                .userId("lss1234")
                .password("1234")
                .socialSecurityNumber("000425-7654321")
                .address("경기도")
                .build();

        memberRepository.saveAll(new ArrayList<Member>(Arrays.asList(member1, member2)));
    }

    @Test
    public void updateAllByAddressTest(){
        memberRepository.updateAllByAddress("경기도");
        memberRepository.findById(110L).ifPresent(member -> log.info(member.toString()));
    }

}
