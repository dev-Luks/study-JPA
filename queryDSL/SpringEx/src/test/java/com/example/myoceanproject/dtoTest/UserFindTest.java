package com.example.myoceanproject.dtoTest;

import com.example.myoceanproject.domain.QUserFindDTO;
import com.example.myoceanproject.domain.UserFindDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.entity.UserFind;
import com.example.myoceanproject.repository.UserFindRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.myoceanproject.entity.QUserFind.userFind;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class UserFindTest {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private UserFindRepository userFindRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest(){
//       시나리오  : 사용자가 이메일 입력을 통해 인증 이메일을 전송하여
//       해당 사용자가 요청한 것인지 확인하기 위해 고유한 값을 저장

        UserFindDTO userFindDTO=new UserFindDTO();
        String email="aaa@aaa.com";
        User user=userRepository.findByUserEmail(email);
        String userUuid= UUID.randomUUID().toString();

        userFindDTO.setUserId(user.getUserId());
        userFindDTO.setUserEmail(user.getUserEmail());
        userFindDTO.setUserUuid(userUuid);

        UserFind userFind=userFindDTO.toentity();
        userFindRepository.save(userFind);
    }

    @Test
    public void findAllTest(){
//       시나리오 : 현재 비밀번호 찾기 요청을 통해 변경하는 회원들의 조회
        List<UserFindDTO> users=jpaQueryFactory.select(new QUserFindDTO(
                userFind.userId,
                userFind.userUuid,
                userFind.userEmail,
                userFind.userFindtime
        )).from(userFind).fetch();

        users.stream().map(UserFindDTO::toString).forEach(log::info);
    }

    @Test
    public void findByEmail(){
//        시나리오 : 현재 비밀번호 찾기 요청을 한 사용자의 이메일을 통해 해당 사용자가 비밀번호 찾기 요청한 것인지 조회
        String email="aaa@aaa.com";
        List<UserFindDTO> users=jpaQueryFactory.select(new QUserFindDTO(
                userFind.userId,
                userFind.userUuid,
                userFind.userEmail,
                userFind.userFindtime
        )).from(userFind).where(userFind.userEmail.eq(email)).fetch();

        users.stream().map(UserFindDTO::toString).forEach(log::info);
    }

    @Test
    public void delete(){
//        시나리오 : 회원이 이메일 인증을 통해 회원임을 확인한 경우 임시 데이터를 삭제하기
        UserFindDTO userFindDTO=jpaQueryFactory.select(new QUserFindDTO(
                userFind.userId,
                userFind.userUuid,
                userFind.userEmail,
                userFind.userFindtime
        )).from(userFind).where(userFind.userId.eq(1L)).fetchOne();

        UserFind userFind=userFindDTO.toentity();
        userFindRepository.delete(userFind);
    }
}
