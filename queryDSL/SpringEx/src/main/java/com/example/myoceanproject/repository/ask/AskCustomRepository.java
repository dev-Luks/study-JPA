package com.example.myoceanproject.repository.ask;

import com.example.myoceanproject.domain.AskDTO;
import com.example.myoceanproject.domain.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AskCustomRepository {
    public AskDTO findAllByDashboard();

    public Page<AskDTO> findAllByStatus(Pageable pageable, Criteria criteria);
    public Page<AskDTO> findAllByStatus(Pageable pageable);

    public Page<AskDTO> findAll(Pageable pageable, Criteria criteria);
    public Page<AskDTO> findAll(Pageable pageable);
}
