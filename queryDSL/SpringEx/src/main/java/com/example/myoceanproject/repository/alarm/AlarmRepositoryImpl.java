package com.example.myoceanproject.repository.alarm;

import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.domain.QAlarmDTO;
import com.example.myoceanproject.type.ReadStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.myoceanproject.entity.QAlarm.alarm;

@Repository
@RequiredArgsConstructor
@Transactional
public class AlarmRepositoryImpl implements AlarmCustomRepository {
//사용자 지정 레파지토리 Impl(구현)

    private final JPAQueryFactory jpaQueryFactory;

    //        유저번호와 포스트번호로 라이크여부를 가려주는 메소드
    //        좋아요를 하지 않았으면 true, 좋아요를 했으면 false
    @Override
    public Page<AlarmDTO> findAllByUserId(Pageable pageable, Long userId){

        List<AlarmDTO> alarms = jpaQueryFactory.select(new QAlarmDTO(
                        alarm.alarmId,
                        alarm.user.userId,
                        alarm.alarmContent,
                        alarm.readStatus,
                        alarm.alarmCategory,
                        alarm.contentId,
                        alarm.createDate
                ))
                .from(alarm)
                .where(alarm.user.userId.eq(userId))
                .orderBy(alarm.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(alarm)
                .where(alarm.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(alarms, pageable, total);
    }

    public void removeAlarm(LocalDateTime today){
        jpaQueryFactory.delete(alarm)
                .where(alarm.createDate.lt(today))
                .execute();
    }

    public boolean checkRead(Long userId){
        return jpaQueryFactory.select(new QAlarmDTO(
                        alarm.alarmId,
                        alarm.user.userId,
                        alarm.alarmContent,
                        alarm.readStatus,
                        alarm.alarmCategory,
                        alarm.contentId,
                        alarm.createDate
                ))
                .from(alarm)
                .where(alarm.user.userId.eq(userId).and(alarm.readStatus.eq(ReadStatus.UNREAD))).fetch().isEmpty();
    }

}
