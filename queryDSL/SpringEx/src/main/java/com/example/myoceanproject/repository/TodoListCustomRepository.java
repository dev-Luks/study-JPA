package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.ToDoListDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoListCustomRepository {
    //    전체 목록 조회
    public List<ToDoListDTO> findAllTodoList(Long userId);
    //    오늘 날짜의 투두리스트 목록 조회
    public List<ToDoListDTO> findAllByToday(Long userId);
    //    선택한 월의 투두리스트 목록 조회
    public List<ToDoListDTO> findAllByMonth(LocalDateTime toDoListSelectDate,Long userId);
    //    투두리스트 수정,삭제 시 아이디로 조회
    public ToDoListDTO findById(Long id);

}
