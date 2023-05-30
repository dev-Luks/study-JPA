//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.ToDoListDTO;
//import com.example.myoceanproject.repository.TodoListCustomRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@SpringBootTest
//@Slf4j
//
//public class TodoListImplTest {
//
//    @Autowired
//    TodoListCustomRepository todoListCustomRepository;
//
//    @Test
//    public void findByMonthTest(){
//        LocalDateTime time = LocalDateTime.of(2022,2,03,00,00);
//        todoListCustomRepository.findAllByMonth(time).stream().map(ToDoListDTO::toString).forEach(log::info);
//    }
//
//
//}
