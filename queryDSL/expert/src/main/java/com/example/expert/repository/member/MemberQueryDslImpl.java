package com.example.expert.repository.member;

import com.example.expert.entity.member.Member;
import com.example.expert.entity.member.QMember;
import com.example.expert.entity.order.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.expert.entity.member.QMember.member;
import static com.example.expert.entity.order.QOrder.order;

@RequiredArgsConstructor
public class MemberQueryDslImpl implements MemberQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<Member> findPaysById_QueryDSL(Long id) {
        return Optional.ofNullable(
                query.select(member)
                        .from(member)
                        .join(member.pays)
                        .fetchJoin()
                        .where(member.id.eq(id))
                        .fetchOne());
    }

    @Override
    public Optional<Member> findOrdersById_QueryDSL(Long id) {
        return Optional.ofNullable(
                query.select(member)
                        .from(member)
                        .join(member.orders)
                        .fetchJoin()
                        .where(member.id.eq(id))
                        .fetchOne());
    }

    @Override
    public Optional<Member> findMemberByOrderId(Long id) {
        return Optional.ofNullable(
                query.select(order.member)
                        .from(order)
                        .where(order.id.eq(id))
                        .fetchOne());
    }
}














