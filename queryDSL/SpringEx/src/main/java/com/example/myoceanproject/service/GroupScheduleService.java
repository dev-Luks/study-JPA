package com.example.myoceanproject.service;

import com.example.myoceanproject.repository.GroupScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("groupSchedule") @Primary
@RequiredArgsConstructor
public class GroupScheduleService {

    private final GroupScheduleRepository groupScheduleRepository;

    // 삭제
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long groupScheduleId) {
        groupScheduleRepository.deleteById(groupScheduleId);
    }

}
