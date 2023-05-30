package com.example.myoceanproject.repository.alarm;

import com.example.myoceanproject.domain.AlarmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//사용자 지정 레파지토리 인터페이스
public interface AlarmCustomRepository {
    public Page<AlarmDTO> findAllByUserId(Pageable pageable, Long userId);
}
