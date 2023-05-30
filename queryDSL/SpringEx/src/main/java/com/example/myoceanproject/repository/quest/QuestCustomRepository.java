package com.example.myoceanproject.repository.quest;

import com.example.myoceanproject.domain.CommunityPostDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.entity.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestCustomRepository {
    public Quest saveQuest(QuestDTO questDTO);
    public Page<QuestDTO> findAll(Pageable pageable);
    public Page<QuestDTO> findAll(Pageable pageable, Criteria criteria);

    public Integer countQuestByUser(Long userId);

    QuestDTO findTodayQuest();

    List<QuestDTO> findAllQuest();
}
