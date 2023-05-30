package com.example.myoceanproject.controller.login;

import com.example.myoceanproject.aspect.annotation.JoinAlarm;
import com.example.myoceanproject.domain.QUserDTO;
import com.example.myoceanproject.domain.QUserFindDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.domain.UserFindDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.entity.UserFind;
import com.example.myoceanproject.repository.UserFindRepository;
import com.example.myoceanproject.service.oAuth.GoogleLoginService;
import com.example.myoceanproject.service.oAuth.KakaoLoginService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.example.myoceanproject.entity.QUser.user;
import static com.example.myoceanproject.entity.QUserFind.userFind;

@Slf4j
@Controller
@RequestMapping("/login/*")
@SessionAttributes("userId")
@RequiredArgsConstructor
public class LoginController {

    private final JPAQueryFactory jpaQueryFactory;
    private final KakaoLoginService kakaoLoginService;
    private final GoogleLoginService googleLoginService;
    private final UserFindRepository userFindRepository;

    // 로그인 페이지
    @GetMapping("/index")
    public String login(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "app/login/login";
    }

    //  로그인시 패스워드 확인을 위한 비동기 요청
    @RequestMapping("/checkUserPassword")
    @ResponseBody
    public String checkuserPassword(@RequestBody ObjectNode saveObj){
//      2가지 이상의 조건으로 검색하기 위해 booleanbuilder객체 생성
        BooleanBuilder builder=new BooleanBuilder();
//        ObjectMapper mapper = new ObjectMapper();   // JSON을 Object화 하기 위한 Jackson ObjectMapper 이용
//        Board board = mapper.treeToValue(saveObj.get("board"), Board.class);
//        User user = mapper.treeToValue(saveObj.get("user"), User.class);

//      로그인시 입력받은 이메일과 비밀번호를 저장
        String userEmail=saveObj.get("userEmail").asText();
        String userPassword = saveObj.get("userPassword").asText();
//        return boardService.update(boardDTO, password);

        builder.and(user.userEmail.eq(userEmail));
        builder.and(user.userPassword.eq(encryption(userPassword)));

//      조건에 맞는 레코드들 검색
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
        )).from(user).where(builder).fetch();
//        log.info(userEmail);
//        log.info(password);

//      1개이상(해당 회원이 있다)이면 "member"문자열 반환, 아니면 "notmember"문자열 반환
        if(users.size()>=1){
            return "member";
        }else{return "notmember";}
    }

    //  로그인 버튼 클릭 시 세션 생성 및 세션에 데이터 저장(일반계정)
    @PostMapping("/loginOk")
    @JoinAlarm
    public String afterLogin(UserDTO userDTO, HttpServletRequest request){

//      세션 생성
        HttpSession session=request.getSession();

//      queryDSL에 2가지 이상의 조건식으로 검색하기 위해 BooleanBuilder 객체 생성 및 사용
        BooleanBuilder builder=new BooleanBuilder();
        builder.and(user.userEmail.eq(userDTO.getUserEmail()));
        builder.and(user.userPassword.eq(encryption(userDTO.getUserPassword())));

//      queryDSL을 사용하여 조건에 해당하는 1개의 레코드 검색
        UserDTO loginUser=jpaQueryFactory.select(new QUserDTO(
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
        )).from(user).where(builder).fetchOne();

//      세션에 검색으로 조회한 값들을 저장
        session.setAttribute("userId",loginUser.getUserId());
        session.setAttribute("userEmail",loginUser.getUserEmail());
        session.setAttribute("userNickname",loginUser.getUserNickname());
        session.setAttribute("userLoginMethod",loginUser.getUserLoginMethod());

        log.info("userId:"+session.getAttribute("userId"));
        log.info("userEmail:"+session.getAttribute("userEmail"));
        log.info("userNickname:"+session.getAttribute("userNickname"));
        log.info("userLoginMethod:"+session.getAttribute("userLoginMethod"));

//      메인으로 이동
        return "redirect:/main/index";
    }

    //  로그아웃 버튼 클릭 시 세션에 저장된 내용 삭제(일반계정)
    @GetMapping("/generalLogout")
    public String logout(HttpServletRequest request,SessionStatus status){
        HttpSession session=request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("userEmail");
        session.removeAttribute("userNickname");
        session.removeAttribute("userLoginMethod");

        status.setComplete();
        return "redirect:/main/index";
    }



    // 비밀번호 찾기 페이지
    @GetMapping("/findPw")
    public String findPw(){
        return "app/login/findPw";
    }

    //  이메일로 비밀번호 찾을때의 비동기식 유효성 검사
    @RequestMapping("/checkUserEmail")
    @ResponseBody
    public String checkUserEmail(@RequestBody String email){

//      이메일에 해당하는 레코드들 검색
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

//      1개 이상(해당 회원이 있다)이면 "available"문자열 반환, 아니면 "unavailable"문자열 반환
        if(users.size()>=1){
            return "available";
        }else{return "unavailable";}
    }

    //  이메일 인증 요청한 사용자를 판별하기 위한 임시 테이블에 데이터를 저장하기 위한 비동기 요청
    @RequestMapping("/requestSaveFind")
    @ResponseBody
    public void saveRequestFind(@RequestBody ObjectNode saveObj){
        UserFindDTO userFindDTO=new UserFindDTO();

//      입력받은 이메일과 랜덤으로 생성한 uuid값을 저장
        String email=saveObj.get("email").asText();
        String uuid=saveObj.get("uuid").asText();

//      이메일에 해당하는 1개의 레코드 검색
        UserDTO users=jpaQueryFactory.select(new QUserDTO(
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
        )).from(user).where(user.userEmail.eq(email)).fetchOne();

//      화면에서 입력받은 값과 조회되 값을 저장 및 db에 저장
        userFindDTO.setUserEmail(email);
        userFindDTO.setUserUuid(uuid);
        userFindDTO.setUserId(users.getUserId());
        UserFind userFind=userFindDTO.toentity();
        userFindRepository.save(userFind);
    }
    // 비밀번호 찾기 요청후 페이지
    @GetMapping("/findPwComplete")
    public String findPwComplete(){
        return "app/login/findPwComplete";
    }

    // 비밀번호 변경 페이지
    @GetMapping("/changePassword")
    public String changePassword(){
        return "app/login/changePassword";
    }

    //  사용자가 이메일 인증 페이지를 들어오면 요청했었던 사용자 정보 삭제
    @RequestMapping("/requestFindUser")
    @ResponseBody
    @Transactional
    @Modifying
    public UserFindDTO giveRequest(@RequestBody String email){

//  url에서 받아온 이메일 주소로 사용자의 이메일에 해당하는 1개의 레코드 검색
        UserFindDTO userFinds=jpaQueryFactory.select(new QUserFindDTO(
                userFind.userId,
                userFind.userUuid,
                userFind.userEmail,
                userFind.userFindtime
        )).from(userFind).where(userFind.userEmail.eq(email)).fetchOne();

//  이메일 인증으로 비밀번호 변경을 하며, 가입되었던 회원임을 확인 하였으므로 임시 데이터 삭제
        jpaQueryFactory.delete(userFind).where(userFind.userEmail.eq(email)).execute();

//  비정상적인 방법(파라미터 변조)으로 접근 여부를 확인하기 위해 요청시 데이터를 반환
        return userFinds;
    }

    //  이메일 인증후 비밀번호 변경시 비동기 요청으로 변경
    @RequestMapping("/saveChangePw")
    @ResponseBody
    @Transactional
    @Modifying
    public void alterPassword(@RequestBody ObjectNode saveObj){
//      조회용 이메일, 변경할 패스워드 값을 ajax 요청으로 불러옴
        String password=saveObj.get("password").asText();
        String email=saveObj.get("email").asText();

//      화면에서 입력받는 변경 패스워드는 DTO객체에 저장, queryDSL로 이메일과 일치하는 1개의 레코드 검색
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPassword(encryption(password));
        userDTO.setUserTotalPoint(0);
        User users=jpaQueryFactory.selectFrom(user).where(user.userEmail.eq(email)).fetchOne();

//      변경된 패스워드 저장
        users.updatePassword(userDTO);
    }
    // 비밀번호 변경 완료 페이지
    @GetMapping("/changePwComplete")
    public String changePwComplete(){
        return "app/login/changePwComplete";
    }

    //    @ResponseBody
    @GetMapping("/kakaoLogin")
    public String  kakaoCallback(@RequestParam String code, HttpSession session, HttpServletRequest request) throws Exception {
        log.info("code:"+code);

        User user=new User();

        String token = kakaoLoginService.getKaKaoAccessToken(code);
        session.setAttribute("token", token);
        user=kakaoLoginService.getKakaoInfo(token);

        log.info("user:"+user);
        if(user.getUserOauthId()!=null){
            session.setAttribute("userId",user.getUserId());
            session.setAttribute("userNickname",user.getUserNickname());
            session.setAttribute("userEmail",user.getUserEmail());
            session.setAttribute("userLoginMethod",user.getUserLoginMethod());

            log.info("userId:"+session.getAttribute("userId"));
            log.info("userEmail:"+session.getAttribute("userEmail"));
            log.info("userNickname:"+session.getAttribute("userNickname"));
            log.info("userLoginMethod:"+session.getAttribute("userLoginMethod"));

            return "redirect:/main/index";
        }
        else{
            if(user.getUserEmail().equals("secureLevelTwo")){
                return "redirect:/join/joinOne?securekakao=1";
            }else{
                return "redirect:/join/joinOne?kakaonotjoin=1";
            }
        }
    }

    @GetMapping("/kakaoLogout")
    public String kakaoLogout(HttpSession session,HttpServletRequest request,SessionStatus status){
        log.info("logout");
        kakaoLoginService.logoutKakao((String)session.getAttribute("token"));
        session=request.getSession();

        session.removeAttribute("userId");
        session.removeAttribute("userEmail");
        session.removeAttribute("userNickname");
        session.removeAttribute("userLoginMethod");

        status.setComplete();
        return "redirect:/main/index";
    }

    @GetMapping("/googleLogin")
    public String googleCallback(Model model, @RequestParam(value = "code") String authCode,HttpSession session) throws Exception{


        User user=googleLoginService.getGoogleAccessTokenInfo(authCode);

        if(user.getUserOauthId()==null){
            return "redirect:/join/joinOne?googlenotjoin=1";
        }
        else{
            session.setAttribute("userId",user.getUserId());
            session.setAttribute("userNickname",user.getUserNickname());
            session.setAttribute("userEmail",user.getUserEmail());
            session.setAttribute("userLoginMethod",user.getUserLoginMethod());

            log.info("userId:"+session.getAttribute("userId"));
            log.info("userEmail:"+session.getAttribute("userEmail"));
            log.info("userNickname:"+session.getAttribute("userNickname"));
            log.info("userLoginMethod:"+session.getAttribute("userLoginMethod"));

            return "redirect:/main/index";
        }
    }

    @GetMapping("/googleLogout")
    public String googleLogout(HttpSession session,HttpServletRequest request,SessionStatus status){
        log.info("logout");
//        googleLoginService.logoutGoogle((String)session.getAttribute("token"));
        session=request.getSession();

        session.removeAttribute("userId");
        session.removeAttribute("userEmail");
        session.removeAttribute("userNickname");
        session.removeAttribute("userLoginMethod");

        status.setComplete();
        return "redirect:/main/index";
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
