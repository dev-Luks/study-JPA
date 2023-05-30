package com.example.myoceanproject.controller.chatting;

import com.example.myoceanproject.domain.ChattingDTO;
import com.example.myoceanproject.domain.ChattingStatusDTO;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.GroupRepositoryImpl;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.GroupService;
import com.example.myoceanproject.service.chattingService.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting/*")
public class ChattingRestController {


    private final ChattingService chattingService;
    private final GroupRepositoryImpl groupRepositoryImpl;
    private final UserRepository userRepository;

    private final GroupService groupService;

    @GetMapping("/groupId/{groupId}")
    public List<ChattingDTO> list(@PathVariable("groupId") Long groupId, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("groupId", groupId);
        Long userId = (Long) session.getAttribute("userId");
        List<ChattingDTO> chattingDTOList= chattingService.showChatting(userId,groupId);
        chattingDTOList.forEach(chattingDTO -> {
            User user =userRepository.findById(chattingDTO.getSenderUserId()).get();

            chattingDTO.setSenderUserFilePath(user.getUserFilePath());
            chattingDTO.setSenderUserFileName(user.getUserFileName());
            chattingDTO.setSenderUserFileUuid(user.getUserFileUuid());
        });

        return chattingDTOList;
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> write(@RequestBody ChattingDTO chattingDTO) throws UnsupportedEncodingException {

        Long userId = chattingDTO.getSenderUserId();
        Long groupId = chattingDTO.getGroupId();

        chattingService.saveMessage(userId, groupId, chattingDTO);


        return new ResponseEntity<>(new String("write success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    //    유저의 아이디를 이용해서 해당 유저가 속한 그룹DTO와 해당 유저가 해당 그룹에서 읽지 않은 채팅 내용을 가져온다
    @GetMapping(value = "/unread")
    public List<GroupDTO> showUnread(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        List<GroupDTO> groupDTOList = chattingService.showGroupAndUnreadChat(userId);
        return groupDTOList;
    }


}