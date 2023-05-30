package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GroupRepository extends JpaRepository<Group, Long> {
    public int countAllByUser_UserId(Long userId);
}