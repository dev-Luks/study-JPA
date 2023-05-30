package com.example.myoceanproject.repository;


import com.example.myoceanproject.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QGroup.group;
import static com.example.myoceanproject.entity.QGroupMember.groupMember;

@Repository
public class MyGroupRepositoryImpl implements MyGroupCustomRepository{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GroupDTO> findAllByUserIdJoin(Pageable pageable, Long userId) {
        List<GroupDTO> myGroups = jpaQueryFactory.select(new QGroupDTO(
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
        ))
                .from(group)
                .join(groupMember)
                .on(groupMember.group.groupId.eq(group.groupId))
                .where(groupMember.user.userId.eq(userId))
                .orderBy(group.groupId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.from(group).join(groupMember).on(groupMember.group.groupId.eq(group.groupId))
                .where(groupMember.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(myGroups, pageable, total);
    }

    @Override
    public Page<GroupDTO> findAllByUserIdJoin(Pageable pageable, Long userId, Criteria criteria) {
        List<GroupDTO> myGroups = jpaQueryFactory.select(new QGroupDTO(
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
        ))
                .from(group)
                .join(groupMember)
                .on(groupMember.group.groupId.eq(group.groupId))
                .where(groupMember.user.userId.eq(userId))
                .orderBy(group.groupId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.from(group).join(groupMember).on(groupMember.group.groupId.eq(group.groupId))
                .where(groupMember.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(myGroups, pageable, total);
    }

    @Override
    public Page<GroupDTO> findAllByUserIdOpen(Pageable pageable, Long userId) {
        List<GroupDTO> myGroups = jpaQueryFactory.select(new QGroupDTO(
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
        ))
                .from(group)
                .where(group.user.userId.eq(userId))
                .orderBy(group.groupId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(group)
                .where(group.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(myGroups, pageable, total);
    }

    @Override
    public Page<GroupDTO> findAllByUserIdOpen(Pageable pageable, Long userId, Criteria criteria) {
        List<GroupDTO> myGroups = jpaQueryFactory.select(new QGroupDTO(
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
        ))
                .from(group)
                .where(group.user.userId.eq(userId))
                .orderBy(group.groupId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        long total = jpaQueryFactory.selectFrom(group)
                .where(group.user.userId.eq(userId))
                .fetch().size();

        return new PageImpl<>(myGroups, pageable, total);
    }
}
