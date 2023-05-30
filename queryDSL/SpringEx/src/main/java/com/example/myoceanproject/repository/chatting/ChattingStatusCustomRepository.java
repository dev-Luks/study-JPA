package com.example.myoceanproject.repository.chatting;

import com.example.myoceanproject.entity.ChattingStatus;
import com.example.myoceanproject.entity.GroupMember;

import java.util.List;

public interface ChattingStatusCustomRepository {

    List<ChattingStatus> findByGroupMemberId(Long groupMemberId);
}
