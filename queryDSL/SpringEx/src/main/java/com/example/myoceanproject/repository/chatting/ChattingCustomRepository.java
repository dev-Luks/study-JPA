package com.example.myoceanproject.repository.chatting;

import com.example.myoceanproject.domain.ChattingDTO;
import com.example.myoceanproject.domain.ChattingStatusDTO;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.domain.GroupMemberDTO;
import com.example.myoceanproject.entity.GroupMember;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ChattingCustomRepository {

    List<GroupMemberDTO> findByGroupId(Long groupId);

    List<GroupDTO> findByUserId(Long userId);

    List<ChattingDTO> findChattingByUserId(Long groupId);

    Long findGroupMemberIdByUserIdAndGroupId(Long userId, Long groupId);

    Integer findUnreadChattingByGroupMemberId(Long userId, Long groupId);

    void updateChattingReadStatus(Long groupMemberId);




}