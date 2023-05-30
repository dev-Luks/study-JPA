package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    public int countAllByUser_UserId(Long userId);
}
