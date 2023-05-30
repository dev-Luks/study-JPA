package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.AskDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.type.AskCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyAskCustomRepository {
    public Page<AskDTO> findAll(Pageable pageable);
    public Page<AskDTO> findAll(Pageable pageable, Criteria criteria);
    public Page<AskDTO> findAllMyAsk(Pageable pageable, Long userId);
    public Page<AskDTO> findAllMyAsk(Pageable pageable, Criteria criteria, Long userId);
    public Page<AskDTO> findAllUserAsk(Pageable pageable, Long userId);
    public Page<AskDTO> findAllUserAsk(Pageable pageable, Criteria criteria, Long userId);
    public Page<AskDTO> findAllByCategory(Pageable pageable, AskCategory askCategory, Long userId);
    public Page<AskDTO> findAllByCategory(Pageable pageable, AskCategory askCategory, Criteria criteria,Long userId);
}
