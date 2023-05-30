package com.example.myoceanproject.controller.communtiy;

import com.example.myoceanproject.aspect.annotation.PostAlarm;
import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.CommunityReplyDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.repository.GroupRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.service.community.CommunityPostService;
import com.example.myoceanproject.service.community.CommunityReplyService;
import com.example.myoceanproject.type.GroupStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
@RequestMapping("/community/*")
public class CommunityRestController {

    private final CommunityPostService communityPostService;
    private final CommunityPostRepository communityPostRepository;

    //  리스트 출력
    @GetMapping("/list")
    public List<CommunityPostDTO> getCommunity(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        // 유저ID 값을 통해 커뮤니티 게시글 전체를 리스트로 출력(아이디 있는 유저, 유저 아이디를 통해 좋아요 체크를 해야하므로 아이디값이 필요함) 
        List<CommunityPostDTO> communityPostDTO= communityPostService.findAllByList(userId);
        return communityPostDTO;
    }

    //  비회원 전용 리스트 출력
    @GetMapping("/list-not-user")
    public List<CommunityPostDTO> getCommunity(){
        // 아이디 없이 커뮤니티 전체 게시글 정보를 출력
        List<CommunityPostDTO> communityPostDTO= communityPostService.findAllByList();
        return communityPostDTO;
    }

    // 동적쿼리 적용한 카테고리 필터(비로그인 유저)
    @GetMapping(value="/list-filter/{communityCategories}")
    public List<CommunityPostDTO> getFilterCommunity(@PathVariable("communityCategories") List<String> communityCategories){
        // 리스트로 받은 카테고리를 통해 communityPostDTO값을 리스트로 받아옴
        List<CommunityPostDTO> communityPostDTO = communityPostService.findBoardByCategory(communityCategories);
        return communityPostDTO;
    }

    // 동적쿼리 적용한 카테고리 필터(로그인 유저)
    @GetMapping(value="/list-filter-login-user/{communityCategories}")
    public List<CommunityPostDTO> getFilterCommunity(@PathVariable("communityCategories") List<String> communityCategories, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        // 리스트로 받은 카테고리를 통해 communityPostDTO값을 리스트로 받아옴(좋아요 함께 필터링)
        List<CommunityPostDTO> communityPostDTO = communityPostService.findBoardByCategory(communityCategories, userId);
        return communityPostDTO;
    }

    //무한스크롤 비회원전용(카테고리 있을 때)
    @GetMapping("/scroll/{page}/{communityCategories}")
    public List<CommunityPostDTO> infiniteScroll(@PathVariable int page, @PathVariable("communityCategories") List<String> communityCategories){
        return communityPostService.findBoardByCategory(page, communityCategories);
    }


    //무한스크롤 비회원전용(카테고리 없을 때)
    @GetMapping("/scroll/{page}/")
    public List<CommunityPostDTO> infiniteScroll(@PathVariable int page){
        return communityPostService.findBoardByCategory(page);
    }

    //무한스크롤 회원전용(카테고리 있을 때)
    @GetMapping("/scroll-user/{page}/{communityCategories}")
    public List<CommunityPostDTO> selectScrollBoards(@PathVariable int page, @PathVariable("communityCategories") List<String> communityCategories, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        return communityPostService.findBoardByCategory(page, communityCategories, userId);
    }

    //무한스크롤 회원전용(카테고리 없을 때)
    @GetMapping("/scroll-user/{page}/")
    public List<CommunityPostDTO> selectScrollBoards(@PathVariable int page, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        return communityPostService.findBoardByCategory(page, userId);
    }

    // 게시글 작성 후 커뮤니티 페이지로 이동
    @PostMapping(value="/index", consumes = "application/json", produces = "text/plain; charset=utf-8")
    @PostAlarm
    public ResponseEntity<String> communityWriting(@RequestBody CommunityPostDTO communityPostDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();

        Long userId = (Long) session.getAttribute("userId");
        // RequestBody로 받아온 communityPostDTO에 userId 정보를 저장
        communityPostDTO.setUserId(userId);
        // communityPostDTO 정보를 communityPost로 추가
        communityPostService.add(communityPostDTO);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }


//  커뮤니티 게시글 작성 시 썸네일 저장
    @PostMapping("/thumbnail")
    public List<CommunityPostDTO> upload(List<MultipartFile> upload) throws IOException {
        // 파일 저장 기본 경로 
        String rootPath = "C:/upload/community";
        String uploadFileName = null;
        List<CommunityPostDTO> files = new ArrayList<>();

        // 파일저장 경로 설정 -> 기본 경로 + 게시글 업로드 날짜
        File uploadPath = new File(rootPath, createDirectoryByNow());
        // 저장 경로가 없다면 저장경로를 새로 만들어준다.
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        // communityPostDTO를 담은 리스트 타입의 업로드 개수만큼 반복을 돌린다.
        for(MultipartFile multipartFile : upload){
            CommunityPostDTO communityPostDTO = new CommunityPostDTO();
            // 파일 이름의 중복을 방지하기 위해 uuid 생성
            UUID uuid = UUID.randomUUID();
            // 실제 파일의 이름을 fileName으로 지정
            String fileName = multipartFile.getOriginalFilename();
            // uuid를 toString하여 fileUuid로 저장
            String fileUuid = uuid.toString();
            // 게시글 업로드 날짜를 communityPostFilePath로 저장
            String communityPostFilePath = createDirectoryByNow();
            // 올린 게시글의 용량을 communityPostFileSize로 저장
            Long communityPostFileSize = multipartFile.getSize();
            // 업로드 파일의 이름을 'uuid_fileName'형태로 저장
            uploadFileName = uuid.toString() + "_" + fileName;

            // communityPostDTO에 파일 이름, Uuid, size, 파일 경로를 저장
            communityPostDTO.setCommunityFileName(fileName);
            communityPostDTO.setCommunityFileUuid(fileUuid);
            communityPostDTO.setCommunityFileSize(multipartFile.getSize());
            communityPostDTO.setCommunityFilePath(communityPostFilePath);

            try{
                // 파일 객체 saveCommunityFile을 uploadPath+uploadFileName으로 저장
                File saveCommunityFile = new File(uploadPath, uploadFileName);
                // saveCommunityFile을 multipartFile로 전송(저장)
                multipartFile.transferTo(saveCommunityFile);
            } catch(Exception e){
                log.error(e.getMessage());
            }
            // files 리스트를 communityPostDTO에 저장
            files.add(communityPostDTO);
        }
        return files;
    }

//  썸네일 출력   
    @GetMapping("/display")
    public byte[] display(String fileName) throws IOException{
        // 파일 경로와 이름을 대조해서 해당 File 객체를 file로 저장
        File file = new File("C:/upload/community", fileName);
        // 전달받은 파일을 복사하여 화면에 출력
        return FileCopyUtils.copyToByteArray(file);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/update", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> communityUpdate(@RequestBody CommunityPostDTO communityPostDTO, HttpServletRequest request) throws UnsupportedEncodingException {

        HttpSession session=request.getSession();
        Long userId = (Long)session.getAttribute("userId");
        // RequestBody로 받아온 communityPostDTO에 userId를 저장
        communityPostDTO.setUserId(userId);
        // groupDTO로 받아온 값의 communityPostId를 엔티티화하여 communityPost를 찾는다.
        CommunityPost communityPost = communityPostRepository.findById(communityPostDTO.getCommunityPostId()).get();
        // 그룹 안에 선언한 update메소드를 통해 communityPostDTO로 받아온 값으로 값을 수정한다.
        communityPost.update(communityPostDTO);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

//  썸네일 취소 시 파일 자동 삭제
    @PostMapping("/delete")
    public void delete(String uploadPath, String fileName){
        // file객체에 지정한 경로와 이름의 파일을 저장
        File file = new File("C:/upload/community", uploadPath + "/" + fileName);
        // 해당 경로에 파일이 존재하면 해당 파일 삭제
        if(file.exists()){
            file.delete();
        }
    }

//  이미지 경로로 오늘의 날짜 지정
    public String createDirectoryByNow(){
        // 날짜 경로 지정 -> /로 구분하여 파일 경로로 사용할 수 있도록 함
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        // 오늘 날짜를 지정한 format으로 출력
        return format.format(now);
    }
}
