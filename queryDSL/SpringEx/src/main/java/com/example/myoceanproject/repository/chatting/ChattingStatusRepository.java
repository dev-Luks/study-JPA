package com.example.myoceanproject.repository.chatting;

import com.example.myoceanproject.entity.ChattingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingStatusRepository extends JpaRepository<ChattingStatus, Long> {
}
