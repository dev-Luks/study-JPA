package com.example.intermediate.entity.file;

import com.example.intermediate.repository.file.ReviewFileDAO;
import com.example.intermediate.type.FileType;
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
public class ReviewFileTest {

    @Autowired
    private ReviewFileDAO reviewFileDAO;

    @Test
    public void saveTest(){
        ReviewFile reviewFile = new ReviewFile();
        reviewFile.setName("리뷰파일.txt");
        reviewFile.setFilePath("2023/04/18");
        reviewFile.setFileSize(1024L);
        reviewFile.setUuid(UUID.randomUUID().toString());
        reviewFile.setFileType(FileType.TEXT);

        reviewFileDAO.save(reviewFile);
    }

    @Test
    public void findByIdTest(){
        reviewFileDAO.findById(21L)
                .ifPresentOrElse(
                        reviewFile -> log.info(reviewFile.getName()),
                                () -> log.info("존재하지 않는 파일입니다."));
    }

    @Test
    public void findAllTest(){
        reviewFileDAO.findAll().stream().map(File::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
        reviewFileDAO.findById(21L).ifPresent(reviewFile -> reviewFile.setName("수정.txt"));
    }

    @Test
    public void deleteTest(){
        reviewFileDAO.findById(21L).ifPresent(reviewFileDAO::delete);
    }

}




















