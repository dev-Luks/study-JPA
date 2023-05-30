package com.example.advanced.entity.member;

import com.example.advanced.repository.member.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.UUID;

@SpringBootTest
@Transactional
@Slf4j
@Rollback(false)
public class FileRepositoryTests {
    @Autowired
    private FileRepository fileRepository;

    @Test
    public void saveTest(){
        com.example.advanced.entity.member.File file = new File();
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

        fileRepository.save(file);
    }

    @Test
    public void findAllTest(){
//        fileRepository.findAll(PageRequest.of(0,5)).map(File::toString).forEach(log::info);
        Page<File> filesPage = fileRepository.findAll(PageRequest.of(0, 5));
        log.info("total: " + filesPage.getTotalElements() );
    }8
}
