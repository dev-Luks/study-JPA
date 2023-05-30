package com.example.myoceanproject.service.oAuth;

import com.example.myoceanproject.domain.QUserDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.oauth.GoogleOAuthRequest;
import com.example.myoceanproject.oauth.GoogleOAuthResponse;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.UserLoginMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static com.example.myoceanproject.entity.QUser.user;

@Slf4j
@Service
public class GoogleLoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public User getGoogleAccessTokenInfo(String authCode) throws Exception{

        try {
            //HTTP Request를 위한 RestTemplate

            RestTemplate restTemplate = new RestTemplate();


            //Google OAuth Access Token 요청을 위한 파라미터 세팅

            GoogleOAuthRequest googleOAuthRequestParam =  new GoogleOAuthRequest();

            googleOAuthRequestParam.setClientId("79401094773-mfgm1b7o40nlokabvnvbai14rllf9bf2.apps.googleusercontent.com");

            googleOAuthRequestParam.setClientSecret("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");

            googleOAuthRequestParam.setCode(authCode);

            googleOAuthRequestParam.setRedirectUri("http://localhost:15000/login/googleLogin");

            googleOAuthRequestParam.setGrantType("authorization_code");


            //JSON 파싱을 위한 기본값 세팅

            //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.

            ObjectMapper mapper = new ObjectMapper();

            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


            //AccessToken 발급 요청

            ResponseEntity<String> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", googleOAuthRequestParam, String.class);


            //Token Request

            GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {

            });


            //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)

            String jwtToken = result.getIdToken();

            String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo").queryParam("id_token", jwtToken).toUriString();


            String resultJson = restTemplate.getForObject(requestUrl, String.class);


            Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});

            String email=userInfo.get("email");
            String nickname=userInfo.get("name");
            String userImage=userInfo.get("picture");
            String userOauthId=userInfo.get("aud").substring(0,11);

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
            )).from(user).where(user.userEmail.eq(email).and(user.userOauthId.like(userOauthId))).fetch();

            UserDTO userDTO=new UserDTO();
            User user=new User();
            if(users.size()==1){
                userDTO.setUserEmail(users.get(0).getUserEmail());
                userDTO.setUserNickname(users.get(0).getUserNickname());
                userDTO.setUserLoginMethod(users.get(0).getUserLoginMethod());
                userDTO.setUserOauthId(users.get(0).getUserOauthId());
                user=userDTO.toEntity();
                user.setUserLoginMethod(UserLoginMethod.change(userDTO.getUserLoginMethod()));
                user.setUserId(users.get(0).getUserId());
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public void logoutGoogle(String token){

    }
}
