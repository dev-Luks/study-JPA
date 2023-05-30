package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.type.PointCheckType;
import com.example.myoceanproject.type.PointType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class PointDTO {

    private Long userId;
    private String groupName;
    private int pointAmountHistory;
    private String pointType;
    private String pointMerchantUid;
    private String pointImpUid;
    private String pointContent;
    private Long pointId;
    private String pointCheckType;

    private LocalDateTime createDate;

    @QueryProjection
    public PointDTO(Long userId, String groupName, int pointAmountHistory, LocalDateTime createDate, PointType pointType, String pointMerchantUid, String pointImpUid, String pointContent, Long pointId, PointCheckType pointCheckType) {
        this.userId = userId;
        this.groupName = groupName;
        this.pointAmountHistory = pointAmountHistory;
        this.createDate = createDate;
        this.pointType=pointType.toString();
        this.pointMerchantUid=pointMerchantUid;
        this.pointImpUid=pointImpUid;
        this.pointContent=pointContent;
        this.pointId=pointId;
        this.pointCheckType=pointCheckType.toString();
    }

    //  포인트의 변화 추이가 계속 새롭게 저장이 된다.
    public Point toEntity(){
        return Point.builder()
                .pointAmountHistory(pointAmountHistory)
                .pointType(PointType.change(pointType))
                .pointMerchantUid(pointMerchantUid)
                .pointImpUid(pointImpUid)
                .pointContent(pointContent)
                .pointCheckType(PointCheckType.change(pointCheckType))
                .groupName(groupName)
                .build();
    }
}
