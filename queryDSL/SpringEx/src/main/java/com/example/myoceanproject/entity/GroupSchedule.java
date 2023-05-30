package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.GroupScheduleDTO;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GROUP_SCHEDULE")
@Getter
@ToString(exclude = "group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupSchedule extends Period{
    @Id
    @GeneratedValue
    private Long groupScheduleId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    @NotNull
    private Group group; //FK
    @NotNull
    private LocalDateTime groupScheduleDate;
    @NotNull
    private LocalDateTime groupScheduleStartTime;
    @NotNull
    private LocalDateTime groupScheduleEndTime;

    public void setGroup(Group group){
        this.group = group;
    }

    @Builder
    public GroupSchedule(LocalDateTime groupScheduleDate, LocalDateTime groupScheduleStartTime, LocalDateTime groupScheduleEndTime) {
        this.groupScheduleDate = groupScheduleDate;
        this.groupScheduleStartTime = groupScheduleStartTime;
        this.groupScheduleEndTime = groupScheduleEndTime;
    }

//  모임 날짜, 시작시간, 종료 시간은 변경이 가능하므로 update
    public void update(GroupScheduleDTO groupScheduleDTO){
        this.groupScheduleDate = groupScheduleDTO.getGroupScheduleDate();
        this.groupScheduleStartTime = groupScheduleDTO.getGroupScheduleStartTime();
        this.groupScheduleEndTime = groupScheduleDTO.getGroupScheduleEndTime();
    }
}
