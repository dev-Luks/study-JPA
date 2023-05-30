package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Alarm;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.AlarmCategory;
import com.example.myoceanproject.type.ReadStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.myoceanproject.entity.QAlarm.alarm;

@Component
@Data
@NoArgsConstructor
@ToString
public class AlarmDTO {

    private Long alarmId;

    private Long userId;

    private String userNickName;

    private String alarmContent;

    private String readStatus;

//    각 데이터로 이동해야하기 때문에 Id가 필요
//    교환일기
//    교환일기가 도착하면 해당일기 상세보기로
//    커뮤니티 게시글
//    좋아요, 댓글 -> 해당 포스트 상세보기
//    그룹
//    만든 그룹에 인원이 참여하면, 관리자가 모임을 승낙하면
//    포인트 등록 ->나의 포인트내역보기
//    퀘스트 -> 나의 완료 퀘스트 목록
//    오늘의 퀘스트 알림 -> 나의 퀘스트로
//    채팅은 채팅 알림이 따로 있고.
    private Long contentId;
    private String alarmCategory;

    private String createDate;

    private List<AlarmDTO> alarmList;
    private int endPage;


@QueryProjection
    public AlarmDTO(Long alarmId, Long userId, String alarmContent, ReadStatus readStatus, AlarmCategory alarmCategory, Long contentId, LocalDateTime createDate) {
        this.alarmId = alarmId;
        this.userId = userId;
        this.alarmContent = alarmContent;
        this.readStatus = readStatus.toString();
        this.alarmCategory = alarmCategory.toString();
        this.contentId = contentId;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("MM월 dd일"));
    }

    public Alarm toEntity(){
        return Alarm.builder()
                .readStatus(ReadStatus.UNREAD)
                .alarmContent(alarmContent)
                .alarmCategory(AlarmCategory.valueOf(alarmCategory))
                .contentId(contentId)
                .build();
    }



}
