package com.example.myoceanproject.repository.chatting;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.ChattingStatus;
import com.example.myoceanproject.type.GroupStatus;
import com.example.myoceanproject.type.ReadStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QChatting.chatting;
import static com.example.myoceanproject.entity.QChattingStatus.chattingStatus;
import static com.example.myoceanproject.entity.QGroup.group;
import static com.example.myoceanproject.entity.QGroupMember.groupMember;

@Repository
@RequiredArgsConstructor
public class ChattingRepositoryImpl implements ChattingCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GroupMemberDTO> findByGroupId(Long groupId) {
        return queryFactory.select(new QGroupMemberDTO(
                groupMember.groupMemberId,
                groupMember.group.groupId,
                groupMember.user.userId
        )).from(groupMember).where(groupMember.group.groupId.eq(groupId)).fetch();
    }

    //    유저 아이디를 받아서 받은 유저 정보와 그룹 멤버 테이블의 유저 정보가 일치할 경우 그룹 아이디를 가지고 옴
    @Override
    public List<GroupDTO> findByUserId(Long userId) {
        return queryFactory.select(new QGroupDTO(
                        group.groupId,
                        group.user.userId,
                        group.user.userFileName,
                        group.user.userFilePath,
                        group.user.userFileSize,
                        group.user.userFileUuid,
                        group.user.userNickname,
                        group.groupName,
                        group.groupCategory,
                        group.groupContent,
                        group.groupPoint,
                        group.groupOverSea,
                        group.groupLocationName,
                        group.groupLocation,
                        group.groupLocationDetail,
                        group.groupParkingAvailable,
                        group.groupMoreInformation,
                        group.groupLocationType,
                        group.groupStatus,
                        group.groupFilePath,
                        group.groupFileName,
                        group.groupFileUuid,
                        group.groupFileSize,
                        group.groupMemberLimit.maxMember,
                        group.groupMemberLimit.minMember,
                        group.createDate,
                        group.updatedDate,
                        group.reason
                )).from(group)
                .join(groupMember)
                .on(groupMember.group.groupId.eq(group.groupId))
                .where(groupMember.user.userId.eq(userId).and(group.groupStatus.eq(GroupStatus.APPROVED))).fetch();
    }

    //    채팅 목록 클릭시 그룹 아이디를 넘겨받음. 이를 통해서 해당 그룹의 채팅 내용을 조회한다.
    @Override
    public List<ChattingDTO> findChattingByUserId(Long groupId) {
        return queryFactory.select(new QChattingDTO(
                chatting.chattingId,
                chatting.senderGroupMember.user.userId,
                chatting.senderGroupMember.user.userNickname,
                chatting.senderGroupMember.user.userFileName,
                chatting.senderGroupMember.user.userFilePath,
                chatting.senderGroupMember.user.userFileSize,
                chatting.senderGroupMember.user.userFileUuid,
                chatting.senderGroupMember.group.groupId,
                chatting.senderGroupMember.group.groupName,
                chatting.senderGroupMember.groupMemberId,
                chatting.chattingContent,
                chatting.createDate,
                chatting.messageType
        )).from(chatting).where(chatting.group.groupId.eq(groupId)).orderBy(chatting.createDate.asc()).fetch();
    }

//    public GroupDTO findGroupByGroupMemberId(Long groupMemberId){
//        return queryFactory.insert(new QChattingDTO(
//                        chatting.chattingId,
//                        chatting.senderGroupMember.user.userId,
//                        chatting.senderGroupMember.user.userNickname,
//                        chatting.senderGroupMember.user.userFileName,
//                        chatting.senderGroupMember.user.userFilePath,
//                        chatting.senderGroupMember.user.userFileSize,
//                        chatting.senderGroupMember.user.userFileUuid,
//                        chatting.senderGroupMember.group.groupId,
//                        chatting.senderGroupMember.group.groupName,
//                        chatting.senderGroupMember.group.groupFilePath,
//                        chatting.senderGroupMember.group.groupFileName,
//                        chatting.senderGroupMember.group.groupFileUuid,
//                        chatting.senderGroupMember.group.groupFileSize,
//                        chatting.senderGroupMember.groupMemberId,
//                        chatting.chattingContent,
//                        chatting.createDate
//                )).;
//    }

    @Override
    public Long findGroupMemberIdByUserIdAndGroupId(Long userId, Long groupId){
        return queryFactory.select(QgroupMember.groupMemberId)
                .from(groupMember)
                .where(groupMember.group.groupId.eq(groupId)
                        .and(groupMember.user.userId.eq(userId)))
                .fetchOne();
    }

    //    안읽은 채팅 개수 반환
    @Override
    public Integer findUnreadChattingByGroupMemberId(Long userId,Long groupId){
        return queryFactory.select(new QChattingStatusDTO(
                chattingStatus.ChattingStatusId,
                chattingStatus.receiverGroupMember.groupMemberId,
                chattingStatus.chatting.chattingId,
                chattingStatus.readStatus
        )).from(chattingStatus).where(chattingStatus.chatting.group.groupId.eq(groupId).and(chattingStatus.receiverGroupMember.user.userId.eq(userId)).and(chattingStatus.readStatus.eq(ReadStatus.UNREAD))).fetch().size();
    }



//    그룹멤버 아이디를 받아와서 그룹 멤버 아이디 값이 매개변수와 동일하면서 chattingStatus가 unread였던 메세지들의 상태를 read로 바꿔준다.

    @Override
    public void updateChattingReadStatus(Long groupMemberId){
//        queryFactory.update(chattingStatus).set(chattingStatus.readStatus, ReadStatus.READ)
//                .where(chattingStatus.receiverGroupMember.groupMemberId.eq(groupMemberId));
    }
}