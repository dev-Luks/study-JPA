package com.example.myoceanproject.controller.join;

import com.example.myoceanproject.aspect.annotation.JoinAlarm;
import com.example.myoceanproject.domain.QUserDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.UserService;
import com.example.myoceanproject.service.oAuth.GoogleJoinService;
import com.example.myoceanproject.service.oAuth.KakaoJoinService;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

import static com.example.myoceanproject.entity.QUser.user;

@Controller
@Slf4j
@RequestMapping("/join/*")
@RequiredArgsConstructor
public class JoinController {


    private final KakaoJoinService kakaoJoinService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JPAQueryFactory jpaQueryFactory;
    private final GoogleJoinService googleJoinService;

    // 첫번째 회원가입 페이지
    @GetMapping("/joinOne")
    public String joinOne(){
        return "app/Join/before_join";
    }

    // 두번째 회원가입 페이지
    @GetMapping("/joinTwo")
    public String joinTwo(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "app/Join/join_in";
    }

    @RequestMapping("/checkUserEmail")
    @ResponseBody
    public String checkSavedUser(@RequestBody String email){
        List<UserDTO> users=jpaQueryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
        )).from(user).where(user.userEmail.eq(email)).fetch();
        log.info("users size:"+users.size());
        if(users.size()>=1){
            return "unavailable";
        }else{return "available";}
    }
    // 세번째 회원가입 페이지
    @PostMapping("/joinThree")
    public String joinThree(UserDTO userDTO, Model model){
        model.addAttribute("userDTO",userDTO);
        return "app/Join/join_in_second";
    }

    @RequestMapping("/checkUserNickname")
    @ResponseBody
    public String checkNickname(@RequestBody String nickname){
        List<UserDTO> users=jpaQueryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
        )).from(user).where(user.userNickname.eq(nickname)).fetch();

        if(users.size()>=1){
            return "unavailable";
        }else{return "available";}
    }

    // 회원가입 버튼 클릭후 메인 홈페이지 이동
    @PostMapping("/joinOk")
    public String joinOk(UserDTO userDTO){
        userDTO.setUserPassword(encryption(userDTO.getUserPassword()));
        userDTO.setUserTotalPoint(0);
        userDTO.setUserAccountStatus("정상");

        userService.saveUser(userDTO);

        return "redirect:/main/index?joingeneral=1";
    }

    //    @ResponseBody
    @GetMapping("/kakaoJoin")
    public String  kakaoCallback(@RequestParam String code, HttpSession session, HttpServletRequest request) throws Exception {
        log.info("code:"+code);

        String token = kakaoJoinService.getKaKaoAccessToken(code);
        session.setAttribute("token", token);
        int exist=kakaoJoinService.getKakaoInfo(token);

        if(exist==0){
            session.invalidate();
            return "redirect:/main/index?joinkakao=1";
        }
        else if(exist==1){
            return "redirect:/login/index?existkakao=1";
        }else if(exist==2){
            return "redirect:/join/joinOne?securekakao=1";
        }else{return "";}
    }

    @GetMapping("/googleJoin")
    public String googleCallback(Model model, @RequestParam(value = "code") String authCode,HttpSession session) throws Exception{

        int exist=googleJoinService.getGoogleAccessTokenInfo(authCode);

        if(exist==0){
            session.invalidate();
            return "redirect:/main/index?joingoogle=1";
        }
        else{
            return "redirect:/login/index?existgoogle=1";
        }
    }

    public String encryption(String userPassword){
        String alg = "AES/CBC/PKCS5Padding";
        //키
        String aesKey = "abcdefghabcdefghabcdefghabcdefgh"; //32byte

        String aesIv = "0123456789abcdef"; //16byte

        //암호화 할 유저 패스워드
        String userPw =userPassword;

        //암호화된 유저 아이디
        String encPw="";


        //알고리즘 aes-256 **********[암호화]**********
        try {

            Cipher cipher = Cipher.getInstance(alg);

            //키로 비밀키 생성
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
            //iv 로 spec 생성
            IvParameterSpec ivParamSpec = new IvParameterSpec(aesIv.getBytes());
            //암호화 적용
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            //암호화 실행
            byte[] encrypted1 = cipher.doFinal(userPw.getBytes("UTF-8")); // ID 암호화(인코딩 설정)
            encPw = Base64.getEncoder().encodeToString(encrypted1); // 암호화 인코딩 후 저장

            System.out.println("암호화된 유저 아이디 -> " + encPw);

        }catch (Exception e) {
            System.out.println("암호화 중 오류 발생 ");
            e.printStackTrace();
        }
        //---------------------------------------------------------------------------
        return encPw;
    }

    public String decryption(String userPassword) {
        String alg = "AES/CBC/PKCS5Padding";
        //키
        String aesKey = "abcdefghabcdefghabcdefghabcdefgh"; //32byte

        String aesIv = "0123456789abcdef"; //16byte

        //복호화 할 유저 패스워드
        String encPw = userPassword;

        //복호화된 유저 패스워드
        String decPw = "";

        //----암호화 해석 코드 **********[복호화]**********
        try {
            Cipher cipher = Cipher.getInstance(alg);

            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(aesIv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            //암호 해석
            byte[] decodedBytes = Base64.getDecoder().decode(encPw);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            decPw = new String(decrypted);


            System.out.println("복호화한 유저 아이디 -> " + decPw);

        }catch (Exception e) {
            e.printStackTrace();
        }
        //---------------------------------------------------------------------------
        return decPw;
    }


}
// 구글 11자리, 카카오 10자리