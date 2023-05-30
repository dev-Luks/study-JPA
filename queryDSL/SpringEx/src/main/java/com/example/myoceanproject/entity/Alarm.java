package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.type.AlarmCategory;
import com.example.myoceanproject.type.ReadStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Alarm")
@Table(name = "TBL_ALARM")
@Getter
@ToString(exclude = "user")
@NoArgsConstructor/*(access = AccessLevel.PROTECTED)*/
public class Alarm extends Period{

    @Id
    @GeneratedValue
    private Long alarmId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user; //FK
    @NotNull
    private String alarmContent;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus; //Enum

    @NotNull
    @Enumerated(EnumType.STRING)
    private AlarmCategory alarmCategory; //Enum

    private Long contentId;

//  양방향
    public void setUser(User user){
        this.user = user;
    }

    @Builder
    public Alarm(User user, String alarmContent, ReadStatus readStatus, AlarmCategory alarmCategory, Long contentId) {
        this.user = user;
        this.readStatus = readStatus;
        this.alarmContent = alarmContent;
        this.alarmCategory = alarmCategory;
        this.contentId = contentId;
    }

//  ReadStatus는 유저가 알람을 보는 순간 READ로 업데이트가 되어야 한다.
    public void updateStatus(){
        this.readStatus = ReadStatus.READ;
    }
    public void setAlarm(AlarmDTO alarmDTO){
        this.alarmContent = alarmDTO.getAlarmContent();
        this.alarmCategory = AlarmCategory.change(alarmDTO.getAlarmCategory());
        this.contentId = alarmDTO.getContentId();
    }

}
