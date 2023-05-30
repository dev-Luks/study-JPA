package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.DiaryDTO;
import com.example.myoceanproject.domain.GroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyGroupCustomRepository {
    public Page<GroupDTO> findAllByUserIdJoin(Pageable pageable, Long userId);
    public Page<GroupDTO> findAllByUserIdJoin(Pageable pageable, Long userId, Criteria criteria);
    public Page<GroupDTO> findAllByUserIdOpen(Pageable pageable, Long userId);
    public Page<GroupDTO> findAllByUserIdOpen(Pageable pageable, Long userId, Criteria criteria);
}
