package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.PointDTO;
import com.example.myoceanproject.type.PointCheckType;
import com.example.myoceanproject.type.PointType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_POINT")
@Getter
@ToString(exclude = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends Period{
    @Id
    @GeneratedValue
    private Long pointId; //PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user; //FK
    @NotNull
    private int pointAmountHistory;


    private String groupName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @NotNull
    private String pointMerchantUid;
    @NotNull
    private String pointImpUid;
    @NotNull
    private String pointContent;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PointCheckType pointCheckType;

    public void changeUser(User user){
        this.user = user;
    }

    //  포인트를 사용하거나 충전했을 때 포인트 히스토리가 하나씩 추가되므로 update가 아니라 insert로 봐야 한다.
    @Builder
    public Point(int pointAmountHistory,PointType pointType,String pointMerchantUid,String pointImpUid,String pointContent,PointCheckType pointCheckType, String groupName) {
        this.pointAmountHistory = pointAmountHistory;
        this.pointType=pointType;
        this.pointMerchantUid=pointMerchantUid;
        this.pointImpUid=pointImpUid;
        this.pointContent=pointContent;
        this.pointCheckType=pointCheckType;
        this.groupName = groupName;
    }

    //  포인트 환불 요청시 관리자가 환불대기->환불승인 상태로 변경시켜주기 위해
    public void update(PointDTO pointDTO){
        this.pointContent=pointDTO.getPointContent();
        this.pointType=PointType.change(pointDTO.getPointType());
        this.pointCheckType=PointCheckType.change(pointDTO.getPointCheckType());
    }
}
