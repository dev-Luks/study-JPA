package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.GroupScheduleDTO;
import com.example.myoceanproject.domain.QGroupDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupMember;
import com.example.myoceanproject.entity.GroupSchedule;
import com.example.myoceanproject.entity.QGroup;
import com.example.myoceanproject.repository.*;
import com.example.myoceanproject.type.GroupStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.myoceanproject.entity.QGroup.*;
import static com.example.myoceanproject.entity.QGroup.group;

@Service @Qualifier("group") @Primary
@RequiredArgsConstructor
public class GroupService implements GroupBoardService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepositoryImpl groupRepositoryImpl;
    private final UserRepository userRepository;
    private final GroupScheduleRepository groupScheduleRepository;

    private final JPAQueryFactory jpaQueryFactory;

//  게시글 등록
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupDTO add(GroupDTO groupDTO){
        Group dto = groupDTO.toEntity();
        dto.setUser(userRepository.findById(groupDTO.getUserId()).get());
        Group groupEntity = groupRepository.save(dto);
        GroupDTO anyone = jpaQueryFactory.select(new QGroupDTO(group.groupId,
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
               group.reason)).from(group).where(group.groupId.eq(groupEntity.getGroupId())).fetchOne();
       return anyone;
    }

//  모임 일정 등록
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSchedule(GroupScheduleDTO groupScheduleDTO){
        GroupSchedule groupSchedule = groupScheduleDTO.toEntity();
        groupSchedule.setGroup(groupRepository.findById(groupScheduleDTO.getGroupId()).get());
        groupScheduleRepository.save(groupSchedule);
    }

//  게시글 목록 보기
    @Override
    public List<GroupDTO> show() {
        return groupRepositoryImpl.findAll();
    }

//  모임 스케줄 보기
    @Override
    public List<GroupScheduleDTO> findAllByGroupId(Long groupId){
        return groupRepositoryImpl.findAllSchedule(groupId);
    };

    public Page<GroupDTO> showGroup(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? groupRepositoryImpl.findAll(pageable) : groupRepositoryImpl.findAll(pageable, criteria);
    }

    @Override
    public GroupDTO find(Long groupId) {
        GroupDTO groupDTO = groupRepositoryImpl.findGroupByGroupId(groupId);
        return groupDTO;
    }

    @Override
    public List<GroupDTO> findTop5BygroupId(Long groupId){
        return groupRepositoryImpl.findGroupTop5ByGroupId(groupId);
    }

    @Override
    public void update(GroupDTO groupDTO) {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);
    }


    public Page<GroupDTO> findAllManage(Pageable pageable, Criteria criteria){
        return criteria.getKeyword().equals("null") ? groupRepositoryImpl.findAllManage(pageable) : groupRepositoryImpl.findAllManage(pageable,criteria);
    }
    public Page<GroupDTO> findAllByStatus(Pageable pageable, GroupStatus groupStatus, Criteria criteria){
        return criteria.getKeyword().equals("null") ? groupRepositoryImpl.findAllByStatus(pageable, groupStatus) : groupRepositoryImpl.findAllByStatus(pageable,groupStatus, criteria);
    }

    public boolean findGroupUser(Long userId, Long groupId){
        return groupRepositoryImpl.countGroupUser(userId, groupId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(Long userId, Long groupId){
        groupRepositoryImpl.deleteGroupMemberByUserId(userId, groupId);
    }

    public int countGroupMember(Long groupId){
        return groupRepositoryImpl.countMember(groupId);
    }

    public GroupDTO findByUserId(Long userId){
        GroupDTO groupDTO = groupRepositoryImpl.findByUserId(userId);
        return groupDTO;
    }
    @Transactional
    public void modifyStatus(Long groupId, String status){
        groupRepository.findById(groupId).get().updateStatus(GroupStatus.change(status));
    }

}
