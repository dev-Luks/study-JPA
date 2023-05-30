package com.example.myoceanproject.service.alarm;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.Alarm;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.UserRepositoryImpl;
import com.example.myoceanproject.repository.alarm.AlarmRepository;
import com.example.myoceanproject.repository.alarm.AlarmRepositoryImpl;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestAchievementRepositoryImpl;
import com.example.myoceanproject.service.PointService;
import com.example.myoceanproject.type.AlarmCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final AlarmRepositoryImpl alarmRepositoryImpl;
    private final QuestAchievementRepositoryImpl achievementRepository;

    private final CommunityLikeRepositoryImpl likeRepositoryImpl;

    private final CommunityReplyRepositoryImpl replyRepositoryImpl;

    private final PointService pointService;

    public void addAlarm(AlarmDTO alarmDTO) {
        if (!alarmDTO.getAlarmCategory().equals(AlarmCategory.TODAY.name())) {
            Alarm alarm = alarmDTO.toEntity();
            alarm.setUser(userRepository.findById(alarmDTO.getUserId()).get());
            alarmRepository.save(alarm);
        } else {
            List<UserDTO> users = userRepositoryImpl.findAllByActive();
            for (UserDTO user : users) {
                Alarm alarm = alarmDTO.toEntity();
                alarm.setUser(userRepository.findById(user.getUserId()).get());
                alarmRepository.save(alarm);
            }
        }
    }

    public void questAlarm(AlarmDTO alarmDTO, Quest quest){
            alarmDTO.setAlarmCategory("QUEST");
            alarmDTO.setAlarmContent(quest.getQuestName() + " 퀘스트 달성! 어떤 보상을 받았는지 확인해보세요!");
            Alarm alarm = alarmDTO.toEntity();
            alarm.setUser(userRepository.findById(alarmDTO.getUserId()).get());
            alarmRepository.save(alarm);

            if(achievementRepository.countBadge(alarmDTO.getUserId()) >= 20){
                alarmDTO.setAlarmContent("뱃지콜렉터 퀘스트 달성!");
                alarm.setUser(userRepository.findById(alarmDTO.getUserId()).get());
                alarmRepository.save(alarm);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(1000);
                pointDTO.setUserId(alarmDTO.getUserId());

                pointService.questReward(pointDTO, quest);

            }else if(achievementRepository.countBadge(alarmDTO.getUserId()) >= 10){
                alarmDTO.setAlarmContent("뱃지콜렉터 퀘스트 달성!");
                alarm.setUser(userRepository.findById(alarmDTO.getUserId()).get());
                alarmRepository.save(alarm);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(2000);
                pointDTO.setUserId(alarmDTO.getUserId());
                pointService.questReward(pointDTO, quest);
            }
    }

    public Page<AlarmDTO> showAlarm(Pageable pageable, Long userId){
        return alarmRepositoryImpl.findAllByUserId(pageable, userId);
    }

    public void modify(Long alarmId){
        alarmRepository.findById(alarmId).get().updateStatus();
    }

    public boolean checkStatus(Long userId){
        return alarmRepositoryImpl.checkRead(userId);
    }

}
