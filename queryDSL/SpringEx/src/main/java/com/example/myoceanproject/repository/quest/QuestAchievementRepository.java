package com.example.myoceanproject.repository.quest;

import com.example.myoceanproject.entity.QuestAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestAchievementRepository extends JpaRepository<QuestAchievement, Long> {
    public int countAllByUser_UserId(Long userId);
}
