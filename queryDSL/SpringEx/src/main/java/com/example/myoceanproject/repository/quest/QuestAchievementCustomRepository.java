package com.example.myoceanproject.repository.quest;

import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.entity.QuestAchievement;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestAchievementCustomRepository {

    Page<QuestDTO> findQuestAchievementByUserId(Long userId, Pageable pegeable);
    Boolean checkDuplicatedById(Long userId, Long questId);
    int countBadge(Long userId);
    int findMonthlyAchievementCount(Long userId, int month);

    QuestAchievement findQuestAchievementByUserIdAndQuest(Long userId, Quest quest);

    List<QuestDTO> findBasicQuestAchievementByUserId(Long userId);
}
