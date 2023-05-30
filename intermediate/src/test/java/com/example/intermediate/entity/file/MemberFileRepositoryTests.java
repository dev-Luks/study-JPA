package com.example.intermediate.entity.file;

import com.example.intermediate.repository.file.MemberFileRepository;
import com.example.intermediate.type.RepresentationalType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class MemberFileRepositoryTests {
    @Autowired
    private MemberFileRepository memberFileRepository;

    @Test
    public void saveTest(){
        MemberFile memberFile1 = MemberFile.builder()
                .name("a.txt")
                .uuid(UUID.randomUUID().toString())
                .filePath("2023/04/24")
                .fileSize(2048L)
                .representationalType(RepresentationalType.REPRESENTATION)
                .build();

        MemberFile memberFile2 = MemberFile.builder()
                .name("b.png")
                .uuid(UUID.randomUUID().toString())
                .filePath("2023/04/23")
                .fileSize(2500L)
                .representationalType(RepresentationalType.NORMAL)
                .build();

        memberFileRepository.saveAll(new ArrayList<MemberFile>(Arrays.asList(memberFile1, memberFile2)));
    }

    @Test
    public void findAllByFileNamesTest(){
        log.info(memberFileRepository.findAllByFileNames(new ArrayList<>(Arrays.asList("a.txt", "b.png"))).toString());
    }
}



















