package com.example.myoceanproject.repository;

import com.example.myoceanproject.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<ToDoList, Long> {
    //update 빼고 crd가능 이유: 업데이트는 set, 즉 변경감지가 필요하다.
}
