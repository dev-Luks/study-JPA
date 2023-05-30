package com.example.advanced.entity.member;

import com.example.advanced.repository.member.FileDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class MemberTests {
    @Autowired
    private FileDAO fileDAO;

    @Test
    public void saveTest(){
        File file = new File();
        Member member = new Member();
        MemberAddress memberAddress = new MemberAddress();

        memberAddress.setMemberAddress("경기도 남양주시 화도읍");
        memberAddress.setMemberAddressDetail("104동 203호");
        memberAddress.setMemberPostcode("12345");

        member.setMemberId("hds1234");
        member.setMemberPassword("1234");
        member.setMemberEmail("tedhan1204@gmail.com");
        member.setMemberAddress(memberAddress);

        file.setFileName("땅문서.png");
        file.setFilePath("2023/04/19");
        file.setFileSize(1024L);
        file.setFileUuid(UUID.randomUUID().toString());
        file.setMember(member);

        fileDAO.save(file);
    }

    @Test
    public void findByIdTest(){
        fileDAO.findById(21L).map(File::toString).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        fileDAO.findAll().stream().map(File::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
        fileDAO.findById(21L).ifPresent(file -> file.getMember().setMemberId("hgd8888"));
    }

    @Test
    public void deleteTest(){
        fileDAO.findById(21L).ifPresent(fileDAO::delete);
    }
}
























