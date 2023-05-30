package com.example.myoceanproject.controller.myPage;

import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/*")
public class MyPageRestController {

    private final UserService userService;
    private final UserRepository userRepository;

    //  커뮤니티 게시글 작성 시 썸네일 저장
    @PostMapping("/thumbnail")
    public List<UserDTO> upload(List<MultipartFile> upload) throws IOException {
        String rootPath = "C:/upload/mypage";
        String uploadFileName = null;
        List<UserDTO> files = new ArrayList<>();

        File uploadPath = new File(rootPath, createDirectoryByNow());
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        for(MultipartFile multipartFile : upload){
            UserDTO userDTO = new UserDTO();
            UUID uuid = UUID.randomUUID();
            String fileName = multipartFile.getOriginalFilename();
            String fileUuid = uuid.toString();
            String userFilePath = createDirectoryByNow();
            Long userFileSize = multipartFile.getSize();
            uploadFileName = uuid.toString() + "_" + fileName;

            log.info("==========================");
            log.info(fileName);
            log.info(fileUuid);
            log.info(uploadFileName);
            log.info(userFilePath);
            log.info(userFileSize+"");
            log.info("==========================");

            userDTO.setUserFileName(fileName);
            userDTO.setUserFileUuid(fileUuid);
            userDTO.setUserFileSize(multipartFile.getSize());
            userDTO.setUserFilePath(userFilePath);

            log.info(userDTO.toString());

            try{
                File saveUserFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveUserFile);

            } catch(Exception e){
                log.error(e.getMessage());
            }
            files.add(userDTO);
        }
        return files;
    }

    //  썸네일 출력
    @GetMapping("/display")
    public byte[] display(String fileName) throws IOException{
        log.info("rest display in");
        log.info("filename"+fileName);
        File file = new File("C:/upload/mypage", fileName);
        log.info("rest display in 2");
        return FileCopyUtils.copyToByteArray(file);
    }

    //  이미지 업데이트 시 기존 이미지는 삭제하고, 업로드가 되어야한다.
//  유저의 프로필 이미지는 삭제버튼이 없으므로, 업데이트 진행 시 삭제를 먼저 하고, 이후 업데이트를 진행해야 한다.
//  썸네일 취소 시 파일 자동 삭제
    @PostMapping("/delete")
    public void delete(String uploadPath, String fileName){
        File file = new File("C:/upload/mypage", uploadPath + "/" + fileName);
        if(file.exists()){
            file.delete();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/update", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> userUpdate(@RequestBody UserDTO userDTO, HttpServletRequest request) throws UnsupportedEncodingException {

        log.info("update controller in");
        System.out.println("업데이트 컨트롤러로 들어옴");
        HttpSession session=request.getSession();
        Long userId = (Long)session.getAttribute("userId");
        userDTO.setUserId(userId);
        /*groupDTO로 받아온 값의 communityPostId를 엔티티화하여 communityPost를 찾는다.*/
        User user = userRepository.findById(userDTO.getUserId()).get();
        /*그룹 안에 선언한 update메소드를 통해 communityPostDTO로 받아온 값으로 값을 수정한다.*/
        user.updateNicknameFile(userDTO);
        log.info(user.toString());

        session.setAttribute("userNickname",userDTO.getUserNickname());

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    //  이미지 경로로 오늘의 날짜 지정
    public String createDirectoryByNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        return format.format(now);
    }

}
