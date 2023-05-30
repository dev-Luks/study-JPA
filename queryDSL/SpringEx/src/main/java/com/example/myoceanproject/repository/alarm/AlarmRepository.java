package com.example.myoceanproject.repository.alarm;

import com.example.myoceanproject.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    public int countAllByUser_UserId(Long userId);
}
