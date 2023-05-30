package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.AskDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.repository.MyAskRepository;
import com.example.myoceanproject.repository.MyAskRepositoryImpl;
import com.example.myoceanproject.repository.ask.AskRepositoryImpl;
import com.example.myoceanproject.type.AskCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AskService {

    private final AskRepositoryImpl askRepositoryImpl;

    @Autowired
    private MyAskRepositoryImpl myAskRepositoryImpl;
    @Autowired
    private MyAskRepository myAskRepository;

    /*카테고리별 자주묻는질문*/
    public Page<AskDTO> showQuestion(Pageable pageable, AskCategory askCategory, Criteria criteria, Long userId){
        return criteria.getKeyword() == null ? myAskRepositoryImpl.findAllByCategory(pageable, askCategory,userId) : myAskRepositoryImpl.findAllByCategory(pageable, askCategory,criteria,userId);
    }

    /*관리자가 작성한 질문 예시*/
    public Page<AskDTO> showAllQuestion(Pageable pageable, Criteria criteria){
        return criteria.getKeyword() == null ? myAskRepositoryImpl.findAll(pageable) : myAskRepositoryImpl.findAll(pageable,criteria);
    }
    /*나의 질문 조회*/
    public Page<AskDTO> showAllMyAsk(Pageable pageable, Criteria criteria, Long userId){
        return criteria.getKeyword() == null ? myAskRepositoryImpl.findAllMyAsk(pageable,userId) : myAskRepositoryImpl.findAllMyAsk(pageable,criteria,userId);
    }
    /*나의 질문 조회*/
    public Page<AskDTO> showAllUserAsk(Pageable pageable, Criteria criteria, Long userId){
        return criteria.getKeyword() == null ? myAskRepositoryImpl.findAllUserAsk(pageable,userId) : myAskRepositoryImpl.findAllUserAsk(pageable,criteria,userId);
    }

    public Page<AskDTO> showAsk(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? askRepositoryImpl.findAll(pageable) : askRepositoryImpl.findAll(pageable,criteria);
    }
    public Page<AskDTO> showAskByStatus(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? askRepositoryImpl.findAllByStatus(pageable) : askRepositoryImpl.findAllByStatus(pageable,criteria);
    }



}
