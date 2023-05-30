package com.example.intermediate.repository.employee;

import com.example.intermediate.entity.employee.Planner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    public Slice<Planner> findByClientCountNot(Pageable pageable, int clientCount);
}
