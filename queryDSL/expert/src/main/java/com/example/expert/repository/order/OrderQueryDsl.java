package com.example.expert.repository.order;

import com.example.expert.entity.member.Member;
import com.example.expert.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderQueryDsl {
//    주문자 정보 조회
    public Optional<Order> findMemberByOrderId(Long id);
}
