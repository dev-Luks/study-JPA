package com.example.myoceanproject.serviceTest;

import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.entity.QuestAchievement;
import com.example.myoceanproject.repository.PointRepository;
import com.example.myoceanproject.repository.PointRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestAchievementRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestRepository;
import com.example.myoceanproject.repository.quest.QuestRepositoryImpl;
import com.example.myoceanproject.service.quest.QuestAchievementService;
import com.example.myoceanproject.service.quest.QuestService;
import com.example.myoceanproject.type.QuestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@Slf4j
public class QuestServiceTest {
    @Autowired
    private QuestAchievementService questAchievementService;

    @Autowired
    private QuestAchievementRepositoryImpl questAchievementRepositoryImpl;
    @Autowired
    private QuestRepositoryImpl questRepositoryImpl;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private PointRepositoryImpl pointRepositoryImpl;

//    @Test
//    public void showTest(){
//        List<QuestDTO> questDTOS = questAchievementService.showMyAchievement(2L);
//        questDTOS.stream().map(QuestDTO::toString).forEach(log::info);
//    }

    @Test
    public void insertTest(){
        for (int i = 0; i < 20 ; i++) {
            QuestDTO questDTO = new QuestDTO();
            questDTO.setQuestName(i + "번째");
            questDTO.setQuestType(QuestType.TODAY.toString());
            Quest quest = questDTO.toEntity();
            questRepository.save(quest);
        }
    }

    @Test
    public void showTest(){
//        log.info(questRepositoryImpl.findTodayQuest().toString());
        log.info(questService.showTodayQuest().toString());
    }

    @Test
    public void findTest(){
        Quest quest = questRepository.findById(20001L).get();
        log.info(pointRepositoryImpl.findRewardPoint(1L,quest).toString());
    }

//    @Test
//    public void showRepositoryTest(){
//        List<QuestDTO> questDTOS = questAchievementRepositoryImpl.findQuestAchievementByUserId(2L);
//        questDTOS.stream().map(QuestDTO::toString).forEach(log::info);
//    }

}
