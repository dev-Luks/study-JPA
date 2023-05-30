package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.repository.MyGroupRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyGroupService {

    @Autowired
    private MyGroupRepositoryImpl myGroupRepositoryImpl;

    public Page<GroupDTO> showOpenGroup(Pageable pageable, Long userId, Criteria criteria){
        return criteria.getKeyword() == null ? myGroupRepositoryImpl.findAllByUserIdOpen(pageable, userId) : myGroupRepositoryImpl.findAllByUserIdOpen(pageable, userId,criteria);
    }
    public Page<GroupDTO> showJoinGroup(Pageable pageable, Long userId, Criteria criteria){
        return criteria.getKeyword() == null ? myGroupRepositoryImpl.findAllByUserIdJoin(pageable, userId) : myGroupRepositoryImpl.findAllByUserIdJoin(pageable, userId,criteria);
    }
}
