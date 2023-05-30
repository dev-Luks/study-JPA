package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
