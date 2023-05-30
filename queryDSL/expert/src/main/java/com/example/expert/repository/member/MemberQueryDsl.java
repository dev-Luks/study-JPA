package com.example.expert.repository.member;

import com.example.expert.entity.member.Member;
import com.example.expert.entity.order.Order;
import com.example.expert.entity.pay.Pay;

import java.util.List;
import java.util.Optional;

public interface MemberQueryDsl {
//    결제 내역 조회
    public Optional<Member> findPaysById_QueryDSL(Long id);

//    주문 내역 조회
    public Optional<Member> findOrdersById_QueryDSL(Long id);
    
//    주문자 정보 조회
    public Optional<Member> findMemberByOrderId(Long id);
}
