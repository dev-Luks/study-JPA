package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.type.DiaryCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.myoceanproject.entity.QDiary.diary;
import static com.example.myoceanproject.entity.QUser.user;

@Repository
@Slf4j
public class DiaryRepositoryImpl implements DiaryCustomRepository{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<DiaryDTO> findAllByUserId(Pageable pageable, Long userId) {
        List<DiaryDTO> posts = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.user.userId.eq(userId).and(diary.receiverUser.userId.isNull()))
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByUserId(Pageable pageable, Long userId, Criteria criteria) {
        List<DiaryDTO> diarys = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.user.userId.eq(userId).and(diary.receiverUser.userId.isNull()))
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(diarys, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByUserIdExchange(Pageable pageable, Long userId) {
        List<DiaryDTO> posts = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.receiverUser.userId.eq(userId).and(diary.diaryCategory.eq(DiaryCategory.OPENDIARY)))
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.receiverUser.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByUserIdExchange(Pageable pageable, Long userId, Criteria criteria) {
        List<DiaryDTO> diarys = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.receiverUser.userId.eq(userId).and(diary.diaryCategory.eq(DiaryCategory.OPENDIARY)))
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.receiverUser.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(diarys, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByDiaryDuration(Pageable pageable,List<String> dateData, Long userId) {
        List<DiaryDTO> diaries = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(dateFilter(dateData).and(diary.user.userId.eq(userId)))
                .orderBy(diary.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(diary)
                .where(dateFilter(dateData).and(diary.user.userId.eq(userId)))
                .fetch().size();

        return new PageImpl<>(diaries, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByDiaryDuration(Pageable pageable, List<String> dateData, Long userId, Criteria criteria) {
        List<DiaryDTO> diaries = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(dateFilter(dateData).and(diary.user.userId.eq(userId)))
                .orderBy(diary.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
//
        long total = jpaQueryFactory.selectFrom(diary)
                .where(dateFilter(dateData).and(diary.user.userId.eq(userId)))
                .fetch().size();
//
        return new PageImpl<>(diaries, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByDiaryByUser(Pageable pageable, Long userId) {
        List<DiaryDTO> diaries = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.user.userId.eq(userId))
                .orderBy(diary.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
//
        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.user.userId.eq(userId))
                .fetch().size();

        log.info("diaries:"+diaries);
        log.info("total:"+total);

        return new PageImpl<>(diaries, pageable, total);
    }

    @Override
    public Page<DiaryDTO> findAllByDiaryByUser(Pageable pageable, Long userId, Criteria criteria) {
        List<DiaryDTO> diaries = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.user.userId.eq(userId))
                .orderBy(diary.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
//
        long total = jpaQueryFactory.selectFrom(diary)
                .where(diary.user.userId.eq(userId))
                .fetch().size();
//
        log.info("diaries:"+diaries);
        log.info("total:"+total);
        return new PageImpl<>(diaries, pageable, total);
    }

    //  receiverUser가 null인 회원들의 수 조회
    @Override
    public int registerReceiverByUser(Long userId,DiaryCategory diaryCategory) {
        List<DiaryDTO> diaries=null;
        try {
            diaries = jpaQueryFactory.select(new QDiaryDTO(
                    diary.diaryId,
                    diary.user.userId,
                    diary.diaryTitle,
                    diary.diaryContent,
                    diary.receiverUser.userId,
                    diary.createDate,
                    diary.updatedDate,
                    diary.diaryCategory
            ))
                    .from(diary)
                    .where(diary.receiverUser.isNull().and(diary.user.userId.ne(userId)).and(diary.diaryCategory.eq(diaryCategory)))
                    .fetch();
        } catch (Exception e) {
            return 0;
        }
        log.info("diaries:"+diaries);
        return diaries.size();
    }

    @Override
    public DiaryDTO findBeforeShareWriter(DiaryCategory diaryCategory) {
        DiaryDTO nullReceiver = jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.diaryCategory.eq(diaryCategory).and(diary.receiverUser.userId.isNull()))
                .fetchOne();
//
        return nullReceiver;
    }

    //
    @Override
    public UserDTO findByUserId(Long userId) {
        UserDTO receiveUser=jpaQueryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
        ))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();
        return receiveUser;
    }

    @Override
    public int checkSameUser(Long userId,DiaryCategory diaryCategory) {
        List<DiaryDTO> diaryDTO=null;
        try {
            diaryDTO=jpaQueryFactory.select(new QDiaryDTO(
                    diary.diaryId,
                    diary.user.userId,
                    diary.diaryTitle,
                    diary.diaryContent,
                    diary.receiverUser.userId,
                    diary.createDate,
                    diary.updatedDate,
                    diary.diaryCategory
            ))
                    .from(diary)
                    .where(diary.user.userId.eq(userId).and(diary.diaryCategory.eq(diaryCategory)).and(diary.receiverUser.userId.isNull()))
                    .fetch();
        } catch (NonUniqueResultException e) {
            return 0;
        }
        return diaryDTO.size();
    }

    @Override
    public List<DiaryDTO> checkTodayWriteDiary(Long userId, DiaryCategory diaryCategory) {
        List<DiaryDTO> diaryDTOS=jpaQueryFactory.select(new QDiaryDTO(
                diary.diaryId,
                diary.user.userId,
                diary.diaryTitle,
                diary.diaryContent,
                diary.receiverUser.userId,
                diary.createDate,
                diary.updatedDate,
                diary.diaryCategory
        ))
                .from(diary)
                .where(diary.user.userId.eq(userId).and(diary.diaryCategory.eq(diaryCategory)))
                .fetch();
        return diaryDTOS;
    }


    private BooleanBuilder dateFilter(List<String> dateData){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        LocalDateTime localDateTime1=LocalDateTime.of(2022,1,1,0,0,0);
        LocalDateTime localDateTime2=LocalDateTime.of(2022,12, 31,23,59,59);

        for(String dateNumber:dateData){
            if(dateNumber.contains("년")) {
                localDateTime1 = localDateTime1.withYear(Integer.parseInt(dateNumber.substring(0, 4)));
                localDateTime2 = localDateTime2.withYear(Integer.parseInt(dateNumber.substring(0, 4)));

                booleanBuilder.and(diary.createDate.between(localDateTime1, localDateTime2));
            }else {
                if (dateNumber.contains("월") && dateNumber.length()!=1) {
                    localDateTime1 = localDateTime1.withMonth(Integer.parseInt(dateNumber.substring(0, 2)));
                    localDateTime2 = localDateTime2.withMonth(Integer.parseInt(dateNumber.substring(0, 2)));
                    booleanBuilder.and(diary.createDate.between(localDateTime1, localDateTime2));
                }
                else{
                    if (dateNumber.contains("일") && dateNumber.length()!=1) {
                        localDateTime1 = localDateTime1.withDayOfMonth(Integer.parseInt(dateNumber.substring(0, 2)));
                        localDateTime2 = localDateTime2.withDayOfMonth(Integer.parseInt(dateNumber.substring(0, 2)));
                        booleanBuilder.and(diary.createDate.between(localDateTime1, localDateTime2));
                    }else{break;}
                }
            }
        }

        return booleanBuilder;
    }

    public int countDiaryByuserId(DiaryCategory diaryCategory, Long userId){
        return Math.toIntExact(jpaQueryFactory.select(diary.diaryId.count()).from(diary)
                .where(diary.user.userId.eq(userId).and(diary.diaryCategory.eq(diaryCategory))).fetchFirst());
    }

}
