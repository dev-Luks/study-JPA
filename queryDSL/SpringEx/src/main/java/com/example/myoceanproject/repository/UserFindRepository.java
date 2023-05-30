package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.UserFind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFindRepository extends JpaRepository<UserFind, Long> {
}
