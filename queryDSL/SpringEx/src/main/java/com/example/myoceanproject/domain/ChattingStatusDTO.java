package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.ChattingStatus;
import com.example.myoceanproject.type.ReadStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
public class ChattingStatusDTO {

    private Long ChattingStatusId;
    private Long receiverGroupMemberId;
    private Long chattingId;
    private ReadStatus readStatus;


    @QueryProjection
    public ChattingStatusDTO(Long chattingStatusId, Long receiverGroupMemberId,Long chattingId, ReadStatus readStatus) {
        this.ChattingStatusId = chattingStatusId;
        this.receiverGroupMemberId = receiverGroupMemberId;
        this.chattingId = chattingId;
        this.readStatus = readStatus;
    }

    public ChattingStatus toEntity(){
        return ChattingStatus.builder()
                .readStatus(readStatus)
                .build();
    }
}