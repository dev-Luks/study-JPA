package com.example.myoceanproject.controller.host;

import com.example.myoceanproject.aspect.annotation.GroupJoinAlarm;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.GroupScheduleDTO;
import com.example.myoceanproject.domain.PointDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupMember;
import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.GroupMemberRepository;
import com.example.myoceanproject.repository.GroupRepository;
import com.example.myoceanproject.repository.PointRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.GroupScheduleService;
import com.example.myoceanproject.service.GroupService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.myoceanproject.entity.QPoint.point;
import static com.example.myoceanproject.entity.QUser.user;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/host/*")
public class HostRestController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final GroupScheduleService groupScheduleService;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final PointRepository pointRepository;

    // 모임 게시글 임시 저장
    @PostMapping(value="/index", consumes = "application/json", produces = "application/json; charset=utf-8")
    public GroupDTO host(@RequestBody GroupDTO groupDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session=request.getSession();

        Long userId = (Long) session.getAttribute("userId");
        // RequestBody로 불러온 groupDTO값에 userId 정보를 저장
        groupDTO.setUserId(userId);

        // Group 테이블 저장
        GroupDTO dto = groupService.add(groupDTO);

        return dto;
    }

    // 모임 멤버 테이블 생성
    @PostMapping("/group-member/{groupId}")
    @GroupJoinAlarm
    public Boolean groupMember(@PathVariable("groupId") Long groupId, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session=request.getSession();
        GroupMember groupMember = new GroupMember();

        // 유저 아이디값 설정
        Long userId = (Long) session.getAttribute("userId");

        // 포인트를 사용한 유저 정보 업데이트를 위해 변수 설정
        User updateUser = jpaQueryFactory.selectFrom(user).where(user.userId.eq(userId)).fetchOne();
        // 유저의 현재 보유 포인트
        int userPoint = updateUser.getUserTotalPoint();
        // 유저가 참여하고자(오픈하고자) 하는 그룹의 포인트
        int groupPayPoint = groupRepository.findById(groupId).get().getGroupPoint();
        // 유저 포인트 업데이트 값(현재포인트 - 그룹 참여 포인트)
        int updateTotalPoint = userPoint - groupPayPoint;

        // 포인트 사용을 위한 유효성 검사 -> 현재 포인트가 그룹 참여 포인트보다 적다면 false값 리턴
        if(updateTotalPoint<0){
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // 포인트 깎기(업데이트)
        updateUser.updateUserTotalPoint(updateTotalPoint);

        // 포인트 테이블에 추가
        PointDTO pointDTO = new PointDTO();
        // 포인트 히스토리에 지불한 포인트 내역 추가
        pointDTO.setPointAmountHistory(groupPayPoint);
        // 포인트 타입 '결제'로 저장
        pointDTO.setPointType("결제");
        // 결제 시간 형식 지정
        pointDTO.setPointMerchantUid(now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
        // group에서 사용했다는 것 표시
        pointDTO.setPointImpUid("group");
        // 포인트 상태 저장
        pointDTO.setPointContent("모임 포인트 결제 완료");
        // 포인트 타입 설정
        pointDTO.setPointCheckType("처음");
        // pointDTO에 그룹 이름 저장
        pointDTO.setGroupName(groupRepository.findById(groupId).get().getGroupName());
        // pointDTO를 엔티티화하여 point에 저장
        Point point=pointDTO.toEntity();
        // point의 유저 정보 수정
        point.changeUser(updateUser);
        // point 정보 저장
        pointRepository.save(point);

        // groupMember에 user 정보 저장
        groupMember.setUser(userRepository.findById(userId).get());
        // groupMember에 group 정보 저장
        groupMember.setGroup(groupRepository.findById(groupId).get());
        // groupMember 정보 저장
        groupMemberRepository.save(groupMember);

        return true;
    }

    // 모임 멤버 취소 시 테이블에서 삭제
    @PostMapping("/group-member-delete/{groupId}")
    public ResponseEntity<String> groupMemberDelete(@PathVariable("groupId") Long groupId, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session=request.getSession();

        // user 저장
        Long userId = (Long) session.getAttribute("userId");

        // 포인트를 사용한 유저 정보 업데이트를 위해 변수 설정
        User updateUser = jpaQueryFactory.selectFrom(user).where(user.userId.eq(userId)).fetchOne();
        // 유저 포인트 변수에 해당 유저가 보유한 전체 포인트 값으로 저장
        int userPoint = updateUser.getUserTotalPoint();
        // group 참여에 필요한 point 값을 groupPayPoint로 저장
        int groupPayPoint = groupRepository.findById(groupId).get().getGroupPoint();
        // now에 현재 시간 저장
        LocalDateTime now = LocalDateTime.now();

        // 포인트 테이블에 추가
        PointDTO pointDTO = new PointDTO();
        // 포인트 히스토리에 지불(환불)한 포인트 내역 추가
        pointDTO.setPointAmountHistory(groupPayPoint);
        // 포인트 타입 환불대기로 변경
        pointDTO.setPointType("환불대기");
        // 결제 시간 형식 지정
        pointDTO.setPointMerchantUid(now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
        // group에서 사용했다는 것 표시
        pointDTO.setPointImpUid("group");
        // 포인트 상태 저장
        pointDTO.setPointContent("모임 포인트 환불 요청");
        // 포인트 타입 설정
        pointDTO.setPointCheckType("이후");
        // pointDTO에 그룹 이름 저장
        pointDTO.setGroupName(groupRepository.findById(groupId).get().getGroupName());
        // pointDTO를 엔티티화하여 point에 저장
        Point point=pointDTO.toEntity();
        // point의 유저 정보 수정
        point.changeUser(updateUser);
        // point 정보 저장
        pointRepository.save(point);

        // 멤버삭제
        groupService.deleteMember(userId, groupId);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    // 스케줄 저장
    @PostMapping(value="/addDate/{groupId}/{groupScheduleStartTime}/{groupScheduleEndTime}", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> schedule(@RequestBody GroupScheduleDTO groupScheduleDTO, @PathVariable("groupId") Long groupId, @PathVariable("groupScheduleStartTime") Date groupScheduleStartTime, @PathVariable("groupScheduleEndTime")Date groupScheduleEndTime) throws UnsupportedEncodingException {
        
        // RequestBody로 받은 groupScheduleDTO에 시작 시간과 끝나는 시간 정보를 저장, js에서 Date 타입으로 받아와, 타입 형식을 맞추기 위해 localDateTime으로 변경 
        groupScheduleDTO.setGroupScheduleStartTime(groupScheduleStartTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        groupScheduleDTO.setGroupScheduleEndTime(groupScheduleEndTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // groupScheduleDTO에 groupId 저장
        groupScheduleDTO.setGroupId(groupId);
        // groupScheduleDTO 정보 저장
        groupService.addSchedule(groupScheduleDTO);
        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }


    // 스케줄 출력
    @GetMapping("/date-list/{groupId}")
    public List<GroupScheduleDTO> getSchedule(@PathVariable("groupId") Long groupId){
        // 그룹ID 정보를 통해 해당 게시글의 스케줄 정보를 groupScheduleDTO 리스트 형식으로 받아옴
        List<GroupScheduleDTO> groupScheduleDTO = groupService.findAllByGroupId(groupId);
        return groupScheduleDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/update", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> hostUpdate(@RequestBody GroupDTO groupDTO, HttpServletRequest request) throws UnsupportedEncodingException {

        HttpSession session=request.getSession();
        Long userId = (Long)session.getAttribute("userId");
        // RequestBody로 받아온 groupDTO에 유저ID값 저장
        groupDTO.setUserId(userId);
        // groupDTO에 모임 상태 저장
        groupDTO.setGroupStatus("임시저장");
        // groupDTO로 받아온 값의 groupId를 엔티티화하여 group을 찾는다.
        Group group = groupRepository.findById(groupDTO.getGroupId()).get();
        log.info(groupDTO.toString());
        // 그룹 안에 선언한 update메소드를 통해 groupDTO로 받아온 값으로 값을 수정한다.
        group.update(groupDTO);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="/update-status", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> hostStatusUpdate(@RequestBody GroupDTO groupDTO, HttpServletRequest request) throws UnsupportedEncodingException {

        HttpSession session=request.getSession();
        Long userId = (Long)session.getAttribute("userId");
        // RequestBody로 받아온 groupDTO에 userId 저장
        groupDTO.setUserId(userId);
        // groupDTO에 모임 상태 정보 저장
        groupDTO.setGroupStatus("승인대기");
        // groupDTO로 받아온 값의 groupId를 엔티티화하여 group을 찾는다.
        Group group = groupRepository.findById(groupDTO.getGroupId()).get();
        // 그룹 안에 선언한 update메소드를 통해 groupDTO로 받아온 값으로 값을 수정한다.
        group.update(groupDTO);

        return new ResponseEntity<>(new String("register success".getBytes(), "UTF-8"), HttpStatus.OK);
    }

    @PostMapping("/thumbnail")
    public List<GroupDTO> upload(List<MultipartFile> upload) throws IOException {
        // 모임 썸네일 이미지 기본 저장 경로 설정
        String rootPath = "C:/upload/group";
        String uploadFileName = null;
        List<GroupDTO> files = new ArrayList<>();
        
        // 파일 경로에 기본경로 + 현재날짜 경로 추가
        File uploadPath = new File(rootPath, createDirectoryByNow());
        // 만약 파일 경로가 없다면 파일 경로 추가
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        // 리스트로 받은 upload 리스트의 개수로 반복문을 돌린다.
        for(MultipartFile multipartFile : upload){
            GroupDTO groupDTO = new GroupDTO();
            // 파일 이름 중복 방지를 위해 uuid 추가
            UUID uuid = UUID.randomUUID();
            // 파일 이름은 기존 파일 이름으로 지정
            String fileName = multipartFile.getOriginalFilename();
            // uuid toString화하여 fileUuid로 저장
            String fileUuid = uuid.toString();
            // 오늘 날짜를 groupFilePath로 저장
            String groupFilePath = createDirectoryByNow();
            // 파일 사이즈를 groupFileSize로 저장
            Long groupFileSize = multipartFile.getSize();
            // 파일 이름을 uuid_fileName 형태로 저장
            uploadFileName = uuid.toString() + "_" + fileName;

            // groupDTO에 파일 이름, Uuid, 파일 크기, 파일 경로 저장
            groupDTO.setGroupFileName(fileName);
            groupDTO.setGroupFileUuid(fileUuid);
            groupDTO.setGroupFileSize(multipartFile.getSize());
            groupDTO.setGroupFilePath(groupFilePath);

            try{
                // 파일 경로 + 파일 이름의 형태로 File객체 saveGroupFile에 저장
                File saveGroupFile = new File(uploadPath, uploadFileName);
                // saveGroupFile을 multipartFile로 전송
                multipartFile.transferTo(saveGroupFile);
            } catch(Exception e){
                log.error(e.getMessage());
            }
            // 파일리스트에 groupDTO 값을 저장
            files.add(groupDTO);
        }
        return files;
    }

    // 썸네일 출력
    @GetMapping("/display")
    public byte[] display(String fileName) throws IOException{
        // 파일 경로+이름과 일치한 file명을 찾아 File 객체인 file을 받음
        File file = new File("C:/upload/group", fileName);
        // 일치하는 파일 정보를 copy
        return FileCopyUtils.copyToByteArray(file);
    }

    // 썸네일 삭제
    @PostMapping("/delete")
    public void delete(String uploadPath, String fileName){
        // 선택한 파일의 경로와 파일 이름의 file을 선택
        File file = new File("C:/upload/group", uploadPath + "/" + fileName);
        // file이 존재하면 해당 파일 삭제
        if(file.exists()){
            file.delete();
        }
    }

    // 모임 스케줄 삭제
    @PostMapping("/delete-schedule/{groupId}/{scheduleDate}")
    public void deleteSchedule(@PathVariable("groupId") Long groupId, @PathVariable("scheduleDate") String scheduleDate){
        // DateFormat 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");
        // groupId로 받은 모든 group정보의 groupSchedule정보를 리스트형태로 받아온다.
        List<GroupScheduleDTO> groupScheduleDTOs = groupService.findAllByGroupId(groupId);
        // groupScheduleDTO 리스트의 전체 길이로 반복문을 돌린다.
        for(GroupScheduleDTO groupScheduleDTO : groupScheduleDTOs){
            // Date 타입의 date에 groupScheduleDTO에서 가져온 schedule정보를 저장
            Date date = java.sql.Timestamp.valueOf(groupScheduleDTO.getGroupScheduleDate());
            // date를 공백을 기준으로 나누고, 0번째 값을 simpleDate로 저장
            String simpleDate = String.valueOf(date).split(" ")[0];
            // 만약 scheduleDate와 simpleDate의 값이 같다면
            if(simpleDate.equals(scheduleDate)){
                // groupScheduleId 값으로 groupScheduleDTO의 ID값을 저장
                Long groupScheduleId = groupScheduleDTO.getGroupScheduleId();
                // 해당 ID 값과 일치한 groupSchedule 정보를 저장
                groupScheduleService.delete(groupScheduleId);
            }
        }
    }

    // 현재 날짜 찾기
    public String createDirectoryByNow(){
        // 현재 날짜를 파일 경로로 이용하기 위해 날짜 출력 format 설정
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        return format.format(now);
    }

    // 이미지 여부 확인
    public boolean checkImageType(File file) throws IOException{
        // 파일 이름이 'image'로 시작하는지로 파악
        return Files.probeContentType(file.toPath()).startsWith("image");
    }


    //썸머노트
    @ResponseBody
    @PostMapping("/summernote")
    public void summerImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=utf-8");
        // 파일 경로 지정
        String uploadPath = "C:/upload/groupUpload/";
        // PrintWriter 객체 out 선언
        PrintWriter out = response.getWriter();
        // file의 기존 이름을 originalFileExtension으로 저장
        String originalFileExtension = file.getOriginalFilename();
        // Uuid를 storedFileName으로 저장
        String storedFileName = UUID.randomUUID().toString().replaceAll("-", "");// + originalFileExtension
        // file 저장 경로 + Uuid + 기존 이름을 파일 이름으로 저장
        file.transferTo(new File(uploadPath + storedFileName + originalFileExtension));
        out.println("/upload/groupUpload/"+storedFileName + originalFileExtension);
        // PrintWriter 닫기
        out.close();
    }


}