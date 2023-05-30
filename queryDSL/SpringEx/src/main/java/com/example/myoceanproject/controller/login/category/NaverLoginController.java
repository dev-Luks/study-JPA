package com.example.myoceanproject.controller.login.category;

import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.oAuth.NaverOAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/naver-login/*")
@RequiredArgsConstructor
public class NaverLoginController {
    
    private final NaverOAuthService naverOAuthService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String naverCallback(Model model, @RequestParam(value = "code") String authCode, HttpSession session) throws Exception{


        String token = naverOAuthService.getNaverAccessToken(authCode);
        String id = naverOAuthService.getNaverIdByToken(token);


        Optional<User> naverUser = userRepository.findByUserOauthId(id);

        if(naverUser.isEmpty()){
            return "redirect:/join/joinOne?navernotjoin=1";
        }
        else{
            session.setAttribute("token", token);
            session.setAttribute("userId",naverUser.get().getUserId());
            session.setAttribute("userNickname",naverUser.get().getUserNickname());
            session.setAttribute("userEmail",naverUser.get().getUserEmail());
            session.setAttribute("userLoginMethod",naverUser.get().getUserLoginMethod());

            log.info("userId:"+session.getAttribute("userId"));
            log.info("userEmail:"+session.getAttribute("userEmail"));
            log.info("userNickname:"+session.getAttribute("userNickname"));
            log.info("userLoginMethod:"+session.getAttribute("userLoginMethod"));

            return "redirect:/main/index";
        }
    }

    @GetMapping("/naverLogout")
    public String naverLogout(HttpSession session, HttpServletRequest request, SessionStatus status){
        log.info("logout");
        log.info("===================================");
        log.info((String)session.getAttribute("token"));
        log.info("===================================");
        naverOAuthService.logoutNaver((String)session.getAttribute("token"));
        session=request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("userEmail");
        session.removeAttribute("userNickname");
        session.removeAttribute("userLoginMethod");
        session.removeAttribute("token");


        status.setComplete();
        return "redirect:/main/index";
    }
}
