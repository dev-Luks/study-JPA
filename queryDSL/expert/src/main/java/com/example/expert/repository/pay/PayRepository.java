package com.example.expert.repository.pay;

import com.example.expert.entity.pay.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

// 리턴 타입으로 레포지토리를 선택한다!
public interface PayRepository extends JpaRepository<Pay, Long>, PayQueryDsl {
}
