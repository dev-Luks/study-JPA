//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.QUserDTO;
//import com.example.myoceanproject.domain.UserDTO;
//import com.example.myoceanproject.entity.User;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.type.UserAccountStatus;
//import com.example.myoceanproject.type.UserLoginMethod;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.util.List;
//
//import static com.example.myoceanproject.entity.QUser.user;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class UserTest {
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Test
//    public void saveUserTest() {
////      시나리오 : 회워가입 1,2페이지를 통해 누적된 회원의 정보를 회원 테이블에 등록하게된다.
////      정보 : 이메일,닉네임,패스워드
////      타입 : 회원가입은 일반(UserLoginMethod.GENERAL), 활성화(UserAccountStatus.ACTIVE)
////      포인트 : 회원가입포인트(5000) - 임의로 지정
//        for (int i = 0; i < 20; i++) {
////      화면에서 이메일, 닉네임, 패스워드를 입력받고 저장하기 위해 DTO객체 생성
//            UserDTO userDTO = new UserDTO();
//
////      입력받은 패스워드를 암호화하기 위한 과정
//            String pw = "qweQWE123!@#";
//            String encryptPw = encryption(pw);
//
////      DTO객체에 화면에서 입력받은 정보와 계정의 추가 상태를 지정한다.
//            userDTO.setUserEmail("whei1234@naver.com");
//            userDTO.setUserNickname("곽두팔");
//            userDTO.setUserPassword(encryptPw);
//            userDTO.setUserLoginMethod(UserLoginMethod.NAVER);
//            userDTO.setUserAccountStatus(UserAccountStatus.BANNED);
//            userDTO.setUserTotalPoint(5000);
//
////      toEntity메서드로 User타입의 엔티티에 저장
//            User user = userDTO.toEntity();
//
////      유저 테이블에 저장
//            userRepository.save(user);
//
//        }
//    }
//
////    @Test
////    public void findAllTest(){
//////      시나리오 : 사용자의 전체 정보를 조회한다.
////        List<UserDTO> users=jpaQueryFactory.select(new QUserDTO(
////                user.userId,
////                user.userPassword,
////                user.userNickname,
////                user.userAccountStatus,
////                user.userFileName,
////                user.userFilePath,
////                user.userFileSize,
////                user.userFileUuid,
////                user.userEmail,
////                user.userLoginMethod,
////                user.userTotalPoint,
////                user.createDate,
////                user.updatedDate
////        )).from(user).fetch();
////
////        log.info("------------------------------------------------------------");
////        users.stream().map(UserDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
////
////
////    @Test
////    public void findById(){
////
//////      시나리오 : 사용자(3L)의 정보를 조회한다.
////        List<UserDTO> users=jpaQueryFactory.select(new QUserDTO(
////                user.userId,
////                user.userPassword,
////                user.userNickname,
////                user.userAccountStatus,
////                user.userFileName,
////                user.userFilePath,
////                user.userFileSize,
////                user.userFileUuid,
////                user.userEmail,
////                user.userLoginMethod,
////                user.userTotalPoint,
////                user.createDate,
////                user.updatedDate
////        )).from(user).where(user.userId.eq(3L)).fetch();
////
////        log.info("------------------------------------------------------------");
////        users.stream().map(UserDTO::toString).forEach(log::info);
////        log.info("------------------------------------------------------------");
////    }
//
//
//
//    public String encryption(String userPassword){
//        String alg = "AES/CBC/PKCS5Padding";
//        //키
//        String aesKey = "abcdefghabcdefghabcdefghabcdefgh"; //32byte
//
//        String aesIv = "0123456789abcdef"; //16byte
//
//        //암호화 할 유저 패스워드
//        String userPw =userPassword;
//
//        //암호화된 유저 아이디
//        String encPw="";
//
//
//        //알고리즘 aes-256 **********[암호화]**********
//        try {
//
//            Cipher cipher = Cipher.getInstance(alg);
//
//            //키로 비밀키 생성
//            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
//            //iv 로 spec 생성
//            IvParameterSpec ivParamSpec = new IvParameterSpec(aesIv.getBytes());
//            //암호화 적용
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//
//            //암호화 실행
//            byte[] encrypted1 = cipher.doFinal(userPw.getBytes("UTF-8")); // ID 암호화(인코딩 설정)
//            encPw = java.util.Base64.getEncoder().encodeToString(encrypted1); // 암호화 인코딩 후 저장
//
//            System.out.println("암호화된 유저 아이디 -> " + encPw);
//
//        }catch (Exception e) {
//            System.out.println("암호화 중 오류 발생 ");
//            e.printStackTrace();
//        }
//        //---------------------------------------------------------------------------
//        return encPw;
//    }
//
//    public String decryption(String userPassword) {
//        String alg = "AES/CBC/PKCS5Padding";
//        //키
//        String aesKey = "abcdefghabcdefghabcdefghabcdefgh"; //32byte
//
//        String aesIv = "0123456789abcdef"; //16byte
//
//        //복호화 할 유저 패스워드
//        String encPw = userPassword;
//
//        //복호화된 유저 패스워드
//        String decPw = "";
//
//        //----암호화 해석 코드 **********[복호화]**********
//        try {
//            Cipher cipher = Cipher.getInstance(alg);
//
//            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
//            IvParameterSpec ivParamSpec = new IvParameterSpec(aesIv.getBytes());
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
//
//            //암호 해석
//            byte[] decodedBytes = Base64.getDecoder().decode(encPw);
//            byte[] decrypted = cipher.doFinal(decodedBytes);
//            decPw = new String(decrypted);
//
//
//            System.out.println("복호화한 유저 아이디 -> " + decPw);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        //---------------------------------------------------------------------------
//        return decPw;
//    }
//}
//
//
//
