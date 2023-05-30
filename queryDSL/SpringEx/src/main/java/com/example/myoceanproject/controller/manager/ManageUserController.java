package com.example.myoceanproject.controller.manager;


import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.repository.UserRepositoryImpl;
import com.example.myoceanproject.service.UserService;
import com.example.myoceanproject.service.community.CommunityReplyService;
import com.example.myoceanproject.type.UserAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
public class ManageUserController {
//    브라우저에서 JSON 타입으로 데이터를 전송하고 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 리턴한다.
//    consumes : 전달받은 데이터의 타입
//    produces : 콜백함수로 결과를 전달할 때의 타입
//    @RequestBody : 전달받은 데이터를 알맞는 매개변수로 주입
//    ResponseEntity : 서버의 상태코드, 응답 메세지 등을 담을 수 있는 타입

    @Autowired
    private UserService userService;

    @GetMapping("/{status}/{page}/{keyword}")
//    public UserDTO list(@RequestBody Criteria criteria){
    public UserDTO list(@PathVariable String status, @PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setKeyword(decodeKeyword);
        criteria.setPage(page);
        criteria.setStatus(status);
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        UserAccountStatus userAccountStatus = (criteria.getStatus().equals("active") ? UserAccountStatus.ACTIVE : UserAccountStatus.BANNED);

        Page<UserDTO> userDTOPage= userService.showUserByStatus(pageable, userAccountStatus, criteria);

        log.info(userDTOPage.getTotalPages() + "end");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserList(userDTOPage.getContent());
        userDTO.setEndPage(userDTOPage.getTotalPages());


        return userDTO;
    }
    @GetMapping("/{page}/{keyword}")
    public UserDTO list(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setKeyword(decodeKeyword);
        criteria.setPage(page);
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<UserDTO> userDTOPage= userService.showAllUser(pageable, criteria);

        log.info(userDTOPage.getTotalPages() + "end");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserList(userDTOPage.getContent());
        userDTO.setEndPage(userDTOPage.getTotalPages());


        return userDTO;
    }

    @PatchMapping (value = "/{status}/{userId}")
    public String updateStatus(@RequestBody UserDTO userDTO, @PathVariable String status, @PathVariable Long userId){
        UserAccountStatus userStatus = status.equals("ACTIVE") ? UserAccountStatus.ACTIVE : UserAccountStatus.BANNED;
        log.info(userStatus + "status");

        userDTO.setUserAccountStatus(String.valueOf(userStatus));
        userDTO.setUserId(userId);

        userService.modify(userDTO);

        return "update success";
    }

}
