package com.example.myoceanproject.repository.chatting;

import com.example.myoceanproject.entity.ChattingStatus;
import com.example.myoceanproject.entity.GroupMember;
import com.example.myoceanproject.repository.chatting.ChattingStatusCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QChattingStatus.chattingStatus;


@Repository
@RequiredArgsConstructor
public class ChattingStatusRepositoryImpl implements ChattingStatusCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChattingStatus> findByGroupMemberId(Long groupMemberId) {
        return queryFactory.select(chattingStatus)
                .from(chattingStatus)
                .where(chattingStatus.receiverGroupMember.groupMemberId.eq(groupMemberId))
                .fetch();
    }



}