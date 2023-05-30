package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyGroupRepository extends JpaRepository<Group,Long> {
    public Group findTopByUserUserIdOrderByGroupIdDesc(Long userId);
}
