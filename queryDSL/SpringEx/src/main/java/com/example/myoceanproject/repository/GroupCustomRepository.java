package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.GroupScheduleDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.type.GroupStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupCustomRepository {
    public GroupDTO findGroupByGroupId(Long groupId);

    public List<GroupDTO> findAll();
    public Page<GroupDTO> findAll(Pageable pageable);
    public Page<GroupDTO> findAll(Pageable pageable, Criteria criteria);
    public List<GroupScheduleDTO> findAllSchedule(Long groupId);

    public List<GroupDTO> findGroupTop5ByGroupId(Long groupId);

    public Page<GroupDTO> findAllManage(Pageable pageable);
    public Page<GroupDTO> findAllManage(Pageable pageable, Criteria criteria);
    public Page<GroupDTO> findAllByStatus(Pageable pageable, GroupStatus groupStatus);
    public Page<GroupDTO> findAllByStatus(Pageable pageable, GroupStatus groupStatus, Criteria criteria);
}
