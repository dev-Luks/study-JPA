package com.example.intermediate.entity.file;

import com.example.intermediate.repository.file.ReviewFileRepository;
import com.example.intermediate.type.FileType;
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
public class ReviewFileTests {
    @Autowired
    private ReviewFileRepository reviewFileRepository;

    @Test
    public void saveTest(){
        ReviewFile reviewFile = ReviewFile.builder()
                .name("후기.png")
                .filePath("2023/04/01")
                .fileSize(5000L)
                .fileType(FileType.IMAGE)
                .uuid(UUID.randomUUID().toString())
                .build();

        reviewFileRepository.save(reviewFile);
    }

    @Test
    public void findByFilePathsTest(){
        log.info(reviewFileRepository.findByFilePaths(new ArrayList<String>(Arrays.asList("2023/04/01", "2023/04/02"))).toString());
    }
}























