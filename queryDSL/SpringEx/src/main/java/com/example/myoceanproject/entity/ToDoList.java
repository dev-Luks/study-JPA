package com.example.myoceanproject.entity;

import com.example.myoceanproject.domain.ToDoListDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_TODOLIST")
@Getter
@ToString(exclude = "user")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ToDoList extends Period{
    @Id
    @GeneratedValue
    private Long toDoListId; //PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user; //FK
    @NotNull
    private String toDoListContent;
    @NotNull
    private LocalDateTime toDoListSelectDate;

    
//  양방향
    public void setUser(User user){
        this.user = user;
    }


    @Builder
    public ToDoList(String toDoListContent, LocalDateTime toDoListSelectDate) {
        this.toDoListContent = toDoListContent;
        this.toDoListSelectDate = toDoListSelectDate;
    }

//  투두리스트의 내용과 날짜는 변경이 가능
    public void update(ToDoListDTO toDoListDTO) {
        this.toDoListContent = toDoListDTO.getToDoListContent();
//        this.toDoListSelectDate = toDoListDTO.getToDoListSelectDate();
    }
}
