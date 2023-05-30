package com.example.advanced.entity.member;

import com.example.advanced.repository.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class memberTests {
    @Autowired
    private MemberDAO memberDAO;

    @Test
    public void savetest(){
        for(int i=0; i<10; i++){
            Member member = new Member();
            File file = new File();

            member.setMemberId("김욱성" + i);
            member.setMemberPassword("1234" + i);

            member.setMemberEmail("asdf@asdf.com");

            file.setFileName("");
        }
    }
}
