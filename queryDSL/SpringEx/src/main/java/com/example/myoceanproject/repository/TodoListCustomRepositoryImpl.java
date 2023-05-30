package com.example.myoceanproject.repository;


import com.example.myoceanproject.domain.QToDoListDTO;
import com.example.myoceanproject.domain.ToDoListDTO;
import com.example.myoceanproject.entity.QToDoList;
import com.example.myoceanproject.entity.ToDoList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.example.myoceanproject.entity.QToDoList.toDoList;

@Repository
@RequiredArgsConstructor
public class TodoListCustomRepositoryImpl implements TodoListCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

//    전체 목록 조회
    @Override
    public List<ToDoListDTO> findAllTodoList(Long userId) {
        return jpaQueryFactory.select(new QToDoListDTO(toDoList.toDoListId, toDoList.user.userId,
                QToDoList.toDoList.toDoListContent,
                QToDoList.toDoList.toDoListSelectDate))
                .from(QToDoList.toDoList)
                .where(toDoList.user.userId.eq(userId)) // userId가 같을 때(그 userId의 목록)
                .fetch();
    }

//   오늘 날짜의 투두리스트 목록 조회
    // LocalDate : 날짜(2022-02-01)
    // LocalTime : 시간(23:59:59)
    // LocalDateTime(LocalDate+LocalTime) : (2022-02-01T23:59:59)
    @Override
    public List<ToDoListDTO> findAllByToday(Long userId) {
        return jpaQueryFactory.select(new QToDoListDTO(toDoList.toDoListId,toDoList.user.userId,
                QToDoList.toDoList.toDoListContent,
                QToDoList.toDoList.toDoListSelectDate))
                .from(QToDoList.toDoList)
                .join(toDoList.user)
                // 선택 날짜의 (현재 날짜의 1일부터, (현재 날짜의, 23시, 59분, 59초까지)) 사이 이고, userId가 같을 때(그 userId의 목록)
                .where(QToDoList.toDoList.toDoListSelectDate.between(LocalDate.now().atStartOfDay()
                        ,LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59))).and(toDoList.user.userId.eq(userId)))
                .orderBy(toDoList.toDoListId.desc())    // 투두리스트 내림차순(최신 게시글순)
                .fetch();
    }

//    선택한 월의 투두리스트 목록 조회
    @Override
    public List<ToDoListDTO> findAllByMonth(LocalDateTime toDoListSelectDate, Long userId) {
        // 선택 날짜의 월의 길이 만큼
        int lastDay = toDoListSelectDate.toLocalDate().lengthOfMonth();
        return jpaQueryFactory.select(new QToDoListDTO(toDoList.toDoListId,toDoList.user.userId,
                toDoList.toDoListContent,
                toDoList.toDoListSelectDate))
                .from(toDoList)
                .join(toDoList.user)
                // 선택 날짜의 (선택 날짜의 1일부터, (선택 날짜의 월의 길이의, 23시, 59분, 59초까지)) 사이 이고, userId가 같을 때(그 userId의 목록)
                .where(toDoList.toDoListSelectDate.between(toDoListSelectDate.withDayOfMonth(1)
                        ,LocalDateTime.of(toDoListSelectDate.withDayOfMonth(lastDay).toLocalDate(),LocalTime.of(23,59,59))).and(toDoList.user.userId.eq(userId)))
                .orderBy(toDoList.toDoListSelectDate.desc())    // 선택 날짜의 내림차순 (최근 날짜순)
                .fetch();
    }

//    투두리스트 수정,삭제 시 아이디로 조회
    @Override
    public ToDoListDTO findById(Long id) {
        return jpaQueryFactory.select(new QToDoListDTO(toDoList.toDoListId,toDoList.user.userId,
                toDoList.toDoListContent,
                toDoList.toDoListSelectDate))
                .from(toDoList)
                .where(toDoList.toDoListId.eq(id))      // userId가 같을 때(그 userId의 목록)
                .fetchOne();
    }

}