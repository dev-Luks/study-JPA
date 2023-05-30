package com.example.myoceanproject.service;

import com.example.myoceanproject.aspect.annotation.DiaryAlarm;
import com.example.myoceanproject.aspect.annotation.ExDiaryAlarm;
import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.DiaryDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.Diary;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.DiaryRepository;
import com.example.myoceanproject.repository.DiaryRepositoryImpl;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.alarm.AlarmService;
import com.example.myoceanproject.type.DiaryCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepositoryImpl diaryRepositoryImpl;

    private final UserRepository userRepository;

    private final DiaryRepository diaryRepository;

    private final AlarmService alarmService;

    public Page<DiaryDTO> showDiary(Pageable pageable, Long userId, Criteria criteria){
        return criteria.getKeyword() == null ? diaryRepositoryImpl.findAllByUserId(pageable, userId) : diaryRepositoryImpl.findAllByUserId(pageable, userId,criteria);
    }
    public Page<DiaryDTO> showExchangeDiary(Pageable pageable, Long userId, Criteria criteria){
        return criteria.getKeyword() == null ? diaryRepositoryImpl.findAllByUserIdExchange(pageable, userId) : diaryRepositoryImpl.findAllByUserIdExchange(pageable, userId,criteria);
    }

    //  게시글 등록
    public Page<DiaryDTO> showSelectDiarys(Pageable pageable, List<String> dateData, Long userId,Criteria criteria) {
        return criteria.getKeyword().equals("null") ? diaryRepositoryImpl.findAllByDiaryDuration(pageable,dateData,userId) : diaryRepositoryImpl.findAllByDiaryDuration(pageable,dateData,userId,criteria);
    }
    //  게시글 등록
    public Page<DiaryDTO> showFirstDiarys(Pageable pageable,Long userId,Criteria criteria) {
        return criteria.getKeyword().equals("null") ? diaryRepositoryImpl.findAllByDiaryByUser(pageable,userId) : diaryRepositoryImpl.findAllByDiaryByUser(pageable,userId,criteria);
    }
    //  게시글 등록
    public int registerShareDiary(Long userId,DiaryCategory diaryCategory) {
        return diaryRepositoryImpl.registerReceiverByUser(userId,diaryCategory);
    }

    public DiaryDTO searchBeforeShareWriter(DiaryCategory diaryCategory){
        return diaryRepositoryImpl.findBeforeShareWriter(diaryCategory);
    }

    public UserDTO findReceiverNullUser(Long userId){
        return diaryRepositoryImpl.findByUserId(userId);
    }

    public int checkSameUser(Long userId,DiaryCategory diaryCategory){
        return diaryRepositoryImpl.checkSameUser(userId,DiaryCategory.OPENDIARY);
    }
    //    public int searchMyDiaryCount(Long userId,DiaryCategory diaryCategory){
//        return diaryRepositoryImpl.checkSameUser(userId);
//    }
    public List<DiaryDTO> todayWriteDiaryDuplicateCheck(Long userId,DiaryCategory diaryCategory) {
        return diaryRepositoryImpl.checkTodayWriteDiary(userId, diaryCategory);
    }

    public int findAllDiary(Long userId){
        return diaryRepository.countAllByUser_UserId(userId);
    }

    public Diary findDetailDiary(Long diaryId){return diaryRepository.findByDiaryId(diaryId);}

    public String determineSaveOrExDiary(Long userId,DiaryDTO diaryDTO){
        Long randomReceiver=0L;

//      화면에서 받아온 데이터를 저장
        Diary diary=diaryDTO.toEntity();
        User users=userRepository.findById(userId).get();
        diary.setUser(users);

//      현재 날짜의 yyyy-MM-dd부분을 문자열 타입으로 변경후 저장
        String todayWriteDate=LocalDateTime.now().toLocalDate().toString();

//      교환일기 로직부분을 처리하기 위해 조회하는 부분
//      1. 수신인 null && !sessionId && 교환 일기(registerShareDiary())
//      2. 수신인 null && sessionId && 교환 일기(checkSameUser())
        int receiverNullSearchNum=registerShareDiary(userId,DiaryCategory.change(diaryDTO.getDiaryCategory()));
        int checkSameUser=checkSameUser(userId,DiaryCategory.OPENDIARY);

        if(diaryDTO.getDiaryCategory().equals("나의 일기")){
            log.info("나의 일기 들어옴");
            return saveMyDiary(userId,diaryDTO);
        }else if(diaryDTO.getDiaryCategory().equals("교환 일기")){
            log.info("교환 일기 들어옴");

//          유저가 당일 처음 교환일기를 등록하는 부분
            if (receiverNullSearchNum == 0 && checkSameUser == 0) {
                log.info("유저가 당일 처음 교환일기를 등록하는 부분");
                return saveTodayFirstExDiary(userId,diary,todayWriteDate);
            }

//          동일한 유저가 교환 일기를 당일 작성후 재작성시 처리 로직
            else if (receiverNullSearchNum == 0 && checkSameUser == 1) {
                log.info("동일한 유저가 교환 일기를 당일 작성후 재작성시 부분");
                return saveDetermineExDiaryAllDays(userId,todayWriteDate,diaryDTO);
            }

//          처음 일기를 등록한 사람이 있으며 다른 회원이 교환일기 신청시 교환되게 하는 부분
            else if (receiverNullSearchNum == 1 && checkSameUser == 0) {
                log.info("처음 일기를 등록한 사람이 있으며 다른 회원이 교환일기 신청시 교환되게 하는 부분");

//              교환일기 신청을 했었던 회원의 정보를 조회(1명)
                randomReceiver =searchBeforeShareWriter(DiaryCategory.OPENDIARY).getUserId();

//              조회된 회원의 정보를 통해 (이전 회원의 번호 && 교환일기 && 수신인이 없을때) 조건으로 조회를 한 1개의 일기를 반환
                Diary diary1=diaryRepository.findByUser_UserIdAndDiaryCategoryAndReceiverUserIsNull(randomReceiver,DiaryCategory.OPENDIARY).get();

//              조회했었던 회원의 수신인 정보를 업데이트
                beforeExDiaryWriterChangeReceiverId(userId,diary1);

//              교환일기 신청을 한 회원의 정보를 저장
                return afterExDiaryWriterRegister(userId, diary1, diaryDTO);
            }
        }
        return "communityIndex";
    }

    /*====================모듈화 작업=========================================================*/
    private String saveMyDiary(Long userId,DiaryDTO diaryDTO){
//      나의 일기 저장(언제든지 작성 가능)
        User myDiary = userRepository.findById(userId).get();
        Diary myDiaryRegist = diaryDTO.toEntity();
        myDiaryRegist.setUser(myDiary);
        diaryRepository.save(myDiaryRegist);
        return "MyDiary";
    }

    private String saveTodayFirstExDiary(Long userId,Diary diary,String todayWriteDate){
//      db에 저장되어있는 회원의 교환일기 작성 목록을 카운트하기 위한 변수
        int checkDuplicate=0;

//      db에 저장되어있는 회원의 교환일기 목록 불러오기
        List<DiaryDTO> searchDiaryCreateDates=todayWriteDiaryDuplicateCheck(userId,DiaryCategory.OPENDIARY);

//      회원이 작성한 시점의 날짜를 포맷에 맞추어 변환후 db의 작성 날짜와 비교
        for(DiaryDTO selectDiary:searchDiaryCreateDates){
            if(selectDiary.getCreateDate().substring(0,10).equals(todayWriteDate)){
//              같으면 1증가
                checkDuplicate++;
            }
        }
//      같은 일자가 있다면
        if(checkDuplicate!=0){
            log.info("같은 일자가 있다면");
            return "alreadyRegisterToday";
        }
//      같은 일자가 없다면
        else{
            log.info("같은 일자가 없다면");
            diaryRepository.save(diary);
            return "standby";
        }
    }

    private String saveDetermineExDiaryAllDays(Long userId,String todayWriteDate,DiaryDTO diaryDTO){
//      db에 저장되어있는 회원의 교환일기 작성 목록을 카운트하기 위한 변수
        int checkDuplicate=0;

//      db에 저장되어있는 회원의 교환일기 목록 불러오기
        List<DiaryDTO> searchDiaryCreateDates=todayWriteDiaryDuplicateCheck(userId,DiaryCategory.OPENDIARY);

//      회원이 작성한 시점의 날짜를 포맷에 맞추어 변환후 db의 작성 날짜와 비교
        for(DiaryDTO selectDiary:searchDiaryCreateDates){
            if(selectDiary.getCreateDate().substring(0,10).equals(todayWriteDate)){
//              같으면 1증가
                checkDuplicate++;
            }
        }
//      같은 일자가 있다면
        if(checkDuplicate!=0){
            return "alreadyRegisterToday";
        }
//      같은 일자가 없다면
        else{
            Diary otherDayExDiary=diaryDTO.toEntity();
            User otherDayWriteExDiary=userRepository.findById(userId).get();
            otherDayExDiary.setUser(otherDayWriteExDiary);
            diaryRepository.save(otherDayExDiary);
            return "otherDayWriteExDiary";
        }
    }

    private void beforeExDiaryWriterChangeReceiverId(Long userId,Diary diary1){
//      이전에 교환일기를 신청한 회원의 정보에 현재 교환일기를 요청한 회원의 번호(receverId)를 저장한다.
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmContent("교환일기가 도착했습니다.");
        alarmDTO.setAlarmCategory("DIARY");
        alarmDTO.setUserId(userId);
        alarmService.addAlarm(alarmDTO);

        diary1.setReceiverUser(userRepository.findById(userId).get());
    }

    private String afterExDiaryWriterRegister(Long userId, Diary diary1, DiaryDTO diaryDTO){
//      신청자와 수신자의 회원 정보를 조회
        User requestExDiaryUser=userRepository.findById(userId).get();
        User responseExDiaryUser=userRepository.findById(diary1.getUser().getUserId()).get();

//      교환일기를 신청을 한 회원의 정보를 화면에서 받아와 저장하며, 이전의 수신인의 정보를 매개변수로 받아와 저장해준다.
        Diary diary2 = diaryDTO.toEntity();
        diary2.setUser(requestExDiaryUser);
        diary2.setReceiverUser(responseExDiaryUser);
        diaryRepository.save(diary2);

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmContent("교환일기가 도착했습니다.");
        alarmDTO.setAlarmCategory("DIARY");
        alarmDTO.setUserId(responseExDiaryUser.getUserId());
        alarmService.addAlarm(alarmDTO);

        return "exchangeDiary";
    }
}
