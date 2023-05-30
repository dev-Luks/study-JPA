package com.example.myoceanproject.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_QUEST_ACHIEVEMENT")
@Getter
@ToString(exclude = {"user", "quest"})
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
public class QuestAchievement extends Period{

    @Id
    @GeneratedValue
    private Long questAchievementId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user; //FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUEST_ID")
    @NotNull
    private Quest quest; //FK

    
//  양방향
    public void setUser(User user){
        this.user = user;
    }
    public void setQuest(Quest quest){
        this.quest = quest;
    }
}
