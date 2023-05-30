package com.example.intermediate.entity.file;

import com.example.intermediate.repository.file.MemberFileDAO;
import com.example.intermediate.type.RepresentationalType;
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
public class MemberFileTest {
    @Autowired
    private MemberFileDAO memberFileDAO;

    @Test
    public void saveTest(){
        MemberFile memberFile = new MemberFile();
        memberFile.setName("테스트.txt");
        memberFile.setFileSize(1024L);
        memberFile.setUuid(UUID.randomUUID().toString());
        memberFile.setFilePath("2023/04/17");
        memberFile.setRepresentationalType(RepresentationalType.REPRESENTATION);

        memberFileDAO.save(memberFile);
    }

    @Test
    public void findByIdTest(){
        memberFileDAO.findById(4L).map(File::toString).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        memberFileDAO.findAll().stream().map(MemberFile::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
        memberFileDAO.findById(4L).ifPresent(memberFile -> memberFile.setName("수정.txt"));
    }

    @Test
    public void deleteTest(){
        memberFileDAO.findById(4L).ifPresent(memberFileDAO::delete);
    }
}
