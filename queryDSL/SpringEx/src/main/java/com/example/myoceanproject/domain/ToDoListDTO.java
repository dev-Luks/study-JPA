package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.ToDoList;
import com.example.myoceanproject.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.common.reflection.XMember;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class ToDoListDTO {
    private Long toDoListId;
    private Long userId;
    private String toDoListContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime toDoListSelectDate;



    @QueryProjection
    public ToDoListDTO(Long toDoListId, Long userId, String toDoListContent, LocalDateTime toDoListSelectDate) {
        this.toDoListId = toDoListId;
        this.userId = userId;
        this.toDoListContent = toDoListContent;
        this.toDoListSelectDate = toDoListSelectDate;
    }

    public ToDoList toEntity(){
        return ToDoList.builder()
                .toDoListContent(toDoListContent)
                .toDoListSelectDate(toDoListSelectDate)
                .build();
    }
}
