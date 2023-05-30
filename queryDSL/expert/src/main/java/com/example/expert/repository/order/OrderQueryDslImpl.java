package com.example.expert.repository.order;

import com.example.expert.entity.member.Member;
import com.example.expert.entity.member.QMember;
import com.example.expert.entity.order.Order;
import com.example.expert.entity.order.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.example.expert.entity.member.QMember.member;
import static com.example.expert.entity.order.QOrder.order;

@RequiredArgsConstructor
public class OrderQueryDslImpl implements OrderQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<Order> findMemberByOrderId(Long id) {
        return Optional.ofNullable(
                query.select(order)
                        .from(order)
                        .join(order.member)
                        .fetchJoin()
                        .where(order.id.eq(id))
                        .fetchOne());
    }
}
