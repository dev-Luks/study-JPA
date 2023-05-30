package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyAskRepository extends JpaRepository<Ask,Long> {
}
