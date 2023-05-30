package com.example.myoceanproject.service.oAuth;

import com.example.myoceanproject.domain.QUserDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.myoceanproject.entity.QUser.user;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverOAuthService {
    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository;


    public String getNaverAccessToken(String code){
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=dF32vzYf2D0cYGgonhWY"); // TODO REST_API_KEY 입력
            sb.append("&client_secret=RVaT__4GAC"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();


            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }




    public int naverProfile(String token) {
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리로 JSON파싱
            log.info(result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            String naverId = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
            String naverName = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
            String email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
            String profileImg = element.getAsJsonObject().get("response").getAsJsonObject().get("profile_image").getAsString();


            List<UserDTO> users =jpaQueryFactory.select(new QUserDTO(
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
            )).from(user).where(user.userOauthId.eq(naverId)).fetch();

            UserDTO userDTO=new UserDTO();
            if(users.size()<1){
                log.info("no join user");
                userDTO.setUserNickname(naverName);
                userDTO.setUserEmail(email);
                userDTO.setUserAccountStatus("정상");
                userDTO.setUserTotalPoint(0);
                userDTO.setUserFilePath(profileImg);
                userDTO.setUserOauthId(naverId);
                User user=userDTO.toEntity();
                user.updateNicknameFile(userDTO);
                user.setUserLoginMethod(UserLoginMethod.NAVER);
                userRepository.save(user);
            }
            br.close();
            return users.size();
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    //  id
    public String getNaverIdByToken(String token) throws Exception{
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String naverId = null;
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리로 JSON파싱

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            naverId = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();

            return naverId;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    // name
    public String getNaverNameByToken(String token) throws Exception{
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String naverName = null;
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리로 JSON파싱

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            naverName = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();

            return naverName;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    // email
    public String getNaverEmailByToken(String token) throws Exception{
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String email = null;
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            //Gson 라이브러리로 JSON파싱

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();

            return email;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public void logoutNaver(String token){
        String reqURL ="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=dF32vzYf2D0cYGgonhWY&client_secret=RVaT__4GAC&access_token="+token+"&service_provider=NAVER";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

//            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            log.info("responseCode : " + responseCode);

            if(responseCode ==400)
                throw new RuntimeException("네이버 로그아웃 도중 오류 발생");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String br_line = "";
            String result = "";
            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            log.info("결과");
            log.info(result);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
