package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupSchedule;
import com.example.myoceanproject.entity.Period;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class GroupScheduleDTO {
    private Long groupId;
    private Long groupScheduleId;
    private LocalDateTime groupScheduleDate;
    private LocalDateTime groupScheduleStartTime;
    private LocalDateTime groupScheduleEndTime;

    @QueryProjection
    public GroupScheduleDTO(Long groupScheduleId, Long groupId, LocalDateTime groupScheduleDate, LocalDateTime groupScheduleStartTime, LocalDateTime groupScheduleEndTime) {
        this.groupScheduleId = groupScheduleId;
        this.groupId = groupId;
        this.groupScheduleDate = groupScheduleDate;
        this.groupScheduleStartTime = groupScheduleStartTime;
        this.groupScheduleEndTime = groupScheduleEndTime;
    }

    //  모임 게시글 작성 시 모임 날짜, 시작 시간, 종료 시간이 처음으로 저장된다.
    public GroupSchedule toEntity(){
        return GroupSchedule.builder()
                .groupScheduleDate(groupScheduleDate)
                .groupScheduleStartTime(groupScheduleStartTime)
                .groupScheduleEndTime(groupScheduleEndTime)
                .build();
    }
}