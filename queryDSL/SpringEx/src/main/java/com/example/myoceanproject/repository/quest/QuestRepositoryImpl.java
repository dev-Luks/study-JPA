package com.example.myoceanproject.repository.quest;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.repository.community.like.CommunityLikeCustomRepository;
import com.example.myoceanproject.type.CommunityCategory;
import com.example.myoceanproject.type.QuestType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityLike.communityLike;
import static com.example.myoceanproject.entity.QCommunityPost.communityPost;
import static com.example.myoceanproject.entity.QCommunityReply.communityReply;
import static com.example.myoceanproject.entity.QQuest.quest;
import static com.example.myoceanproject.entity.QQuestAchievement.questAchievement;

@Repository
@RequiredArgsConstructor
public class QuestRepositoryImpl implements QuestCustomRepository {
//사용자 지정 레파지토리 Impl(구현)

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Quest saveQuest(QuestDTO questDTO){
        return questDTO.toEntity();
    }

    @Override
    public Page<QuestDTO> findAll(Pageable pageable){
        List<QuestDTO> quests = jpaQueryFactory.select(new QQuestDTO(
                        quest.questId,
                        quest.questCategory,
                        quest.questName,
                        quest.questContent,
                        quest.questType,
                        quest.questDeadLine,
                        quest.questPoint,
                        quest.questFilePath,
                        quest.questFileName,
                        quest.questFileUuid,
                        quest.questFileSize,
                        quest.createDate
                ))
                .from(quest)
                .orderBy(quest.questId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(quest)
                .fetch().size();

        return new PageImpl<>(quests, pageable, total);
    }
    @Override
    public Page<QuestDTO> findAll(Pageable pageable, Criteria criteria){
//        검색어가 있는 경우
        List<QuestDTO> quests = jpaQueryFactory.select(new QQuestDTO(
                        quest.questId,
                        quest.questCategory,
                        quest.questName,
                        quest.questContent,
                        quest.questType,
                        quest.questDeadLine,
                        quest.questPoint,
                        quest.questFilePath,
                        quest.questFileName,
                        quest.questFileUuid,
                        quest.questFileSize,
                        quest.createDate
                ))
                .from(quest)
                .where(quest.questName.contains(criteria.getKeyword()))
                .orderBy(quest.questId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(quest)
                .where(quest.questName.contains(criteria.getKeyword()))
                .fetch().size();

        return new PageImpl<>(quests, pageable, total);
    }

    @Override
    public Integer countQuestByUser(Long userId){
        return Math.toIntExact(jpaQueryFactory.select(questAchievement.questAchievementId.count())
                .from(questAchievement)
                .where(questAchievement.user.userId.eq(userId))
                .fetchFirst());
    }

    @Override
    public QuestDTO findTodayQuest(){
        return jpaQueryFactory.select(new QQuestDTO(
                    quest.questId,
                    quest.questCategory,
                    quest.questName,
                    quest.questContent,
                    quest.questType,
                    quest.questDeadLine,
                    quest.questPoint,
                    quest.questFilePath,
                    quest.questFileName,
                    quest.questFileUuid,
                    quest.questFileSize,
                    quest.createDate
        )).from(quest).where(quest.questDeadLine.eq(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .fetchOne();
    }

    @Override
    public List<QuestDTO> findAllQuest(){
        return jpaQueryFactory.select(new QQuestDTO(
                        quest.questId,
                        quest.questCategory,
                        quest.questName,
                        quest.questContent,
                        quest.questType,
                        quest.questDeadLine,
                        quest.questPoint,
                        quest.questFilePath,
                        quest.questFileName,
                        quest.questFileUuid,
                        quest.questFileSize,
                        quest.createDate
                )).from(quest)
                .where(quest.questType.eq(QuestType.BASIC))
                .orderBy(quest.questId.asc())
                .fetch();
    }

}
