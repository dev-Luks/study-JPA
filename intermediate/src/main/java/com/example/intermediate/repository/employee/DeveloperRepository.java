package com.example.intermediate.repository.employee;

import com.example.intermediate.entity.employee.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
//    Pageable(page, size)
//     전달받은 페이지와 정보 개수를 담는 객체
//    Page<T>
//     조회된 정보와 전체 개수 등 다양한 기능을 제공한다.
    public Page<Developer> findAll(Pageable pageable);
}
