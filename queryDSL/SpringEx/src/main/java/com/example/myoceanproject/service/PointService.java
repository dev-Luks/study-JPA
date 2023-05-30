package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.PointDTO;
import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.entity.Quest;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.PointRepository;
import com.example.myoceanproject.repository.PointRepositoryImpl;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.PointType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepositoryImpl pointRepositoryImpl;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;



    public List<PointDTO> findAllPointByUser(Long userId){return pointRepositoryImpl.findAllPointByUser(userId);}
    public List<PointDTO> findAllPayPointByUser(Long userId,PointType pointType){return pointRepositoryImpl.findAllPayPoint(userId,pointType);}
    public List<PointDTO> findAllRefundPointByAllUser(PointType pointType){return pointRepositoryImpl.findAllRefundPoint(pointType);}

    public void questReward(PointDTO pointDTO, Quest quest){
        pointDTO.setPointType("보상");
        pointDTO.setPointMerchantUid(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
        pointDTO.setPointContent(quest.getQuestName() + " 퀘스트 보상 지급");
        pointDTO.setPointCheckType("보상");
        Point point = pointDTO.toEntity();
        point.changeUser(userRepository.findById(pointDTO.getUserId()).get());

        pointRepository.save(point);

        int updateTotalPoint=point.getUser().getUserTotalPoint()+Integer.parseInt(String.valueOf(pointDTO.getPointAmountHistory()));
        point.getUser().updateUserTotalPoint(updateTotalPoint);
    }

    public Integer showRewardPointTotal(Long userId){
        return pointRepositoryImpl.findAllRewardPoint(userId);
    }

    public void deleteRewardPoint(Long userId, Quest quest){
        PointDTO pointDTO= pointRepositoryImpl.findRewardPoint(userId, quest);
        log.info(pointDTO.toString());
        pointRepository.deleteById(pointDTO.getPointId());
    }
}
