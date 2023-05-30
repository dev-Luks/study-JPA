package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.PointDTO;
import com.example.myoceanproject.domain.QPointDTO;
import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.type.PointCheckType;
import com.example.myoceanproject.type.PointType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static com.example.myoceanproject.entity.QPoint.point;

@Repository
public class PointRepositoryImpl implements PointCustomRepository{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PointDTO> findAllPointByUser(Long userId) {
        List<PointDTO> pointDTOS=jpaQueryFactory.select(new QPointDTO(
                point.user.userId,
                point.groupName,
                point.pointAmountHistory,
                point.createDate,
                point.pointType,
                point.pointMerchantUid,
                point.pointImpUid,
                point.pointContent,
                point.pointId,
                point.pointCheckType
        )).from(point).where(point.user.userId.eq(userId)).orderBy(point.pointId.desc()).fetch();
        return pointDTOS;
    }

    @Override
    public List<PointDTO> findAllPayPoint(Long userId,PointType pointType) {
        List<PointDTO> pointDTOS=jpaQueryFactory.select(new QPointDTO(
                point.user.userId,
                point.groupName,
                point.pointAmountHistory,
                point.createDate,
                point.pointType,
                point.pointMerchantUid,
                point.pointImpUid,
                point.pointContent,
                point.pointId,
                point.pointCheckType
        )).from(point).where(point.user.userId.eq(userId).and(point.pointType.eq(pointType)).and(point.pointCheckType.eq(PointCheckType.BEFOREREFUND))).orderBy(point.pointId.desc()).fetch();
        return pointDTOS;
    }

    @Override
    public List<PointDTO> findAllRefundPoint(PointType pointType) {
        List<PointDTO> pointDTOS=jpaQueryFactory.select(new QPointDTO(
                point.user.userId,
                point.groupName,
                point.pointAmountHistory,
                point.createDate,
                point.pointType,
                point.pointMerchantUid,
                point.pointImpUid,
                point.pointContent,
                point.pointId,
                point.pointCheckType
        )).from(point).where(point.pointType.eq(pointType).and(point.pointCheckType.eq(PointCheckType.BEFOREREFUND))).orderBy(point.pointId.desc()).fetch();
        return pointDTOS;
    }

    @Override
    public Integer findAllRewardPoint(Long userId){
        return jpaQueryFactory.select(point.pointAmountHistory.sum())
                .where(point.pointType.eq(PointType.REWARD).and(point.user.userId.eq(userId)))
                .from(point)
                .fetchOne();
    }

    @Override
    public PointDTO findRewardPoint(Long userId, Quest quest){
        return jpaQueryFactory.select(new QPointDTO(
                point.user.userId,
                point.groupName,
                point.pointAmountHistory,
                point.createDate,
                point.pointType,
                point.pointMerchantUid,
                point.pointImpUid,
                point.pointContent,
                point.pointId,
                point.pointCheckType
        )).from(point)
                .where(point.user.userId.eq(userId).and(point.createDate.between(LocalDate.now().atStartOfDay()
                , LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59))).and(point.pointContent.contains(quest.getQuestName()))))
                .fetchOne();
    }


}
