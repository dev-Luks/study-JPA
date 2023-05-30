package com.example.myoceanproject.service;

import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.GroupScheduleDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupSchedule;
import org.hibernate.Criteria;

import java.util.List;

public interface GroupBoardService {
    //  게시글 등록
    public GroupDTO add(GroupDTO groupDTO);

    // 모임 일정 등록
    public void addSchedule(GroupScheduleDTO groupScheduleDTO);

    //  게시글 목록
    public List<GroupDTO> show();

    public List<GroupScheduleDTO> findAllByGroupId(Long groupId);

    //  게시글 조회
    public GroupDTO find(Long groupId);

    //  게시글 5개 조회
    public List<GroupDTO> findTop5BygroupId(Long groupId);

    //  게시글 수정
    public void update(GroupDTO groupDTO);

    //  게시글 삭제
    public void delete(Long GroupId);



}