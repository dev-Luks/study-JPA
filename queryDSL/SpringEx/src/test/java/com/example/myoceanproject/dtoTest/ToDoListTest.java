//package com.example.myoceanproject.dtoTest;
//
//import com.example.myoceanproject.domain.QToDoListDTO;
//import com.example.myoceanproject.domain.ToDoListDTO;
//import com.example.myoceanproject.entity.ToDoList;
//import com.example.myoceanproject.entity.User;
//import com.example.myoceanproject.repository.TodoListCustomRepository;
//import com.example.myoceanproject.repository.TodoListRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.example.myoceanproject.entity.QGroup.group;
//import static com.example.myoceanproject.entity.QToDoList.toDoList;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class ToDoListTest {
//    @Autowired
//    private TodoListRepository todoListRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    TodoListCustomRepository todoListCustomRepository;
//
//    @Test
//    public void saveTest(){
////        시나리오 : 유저가 투두리스트를 작성하면 작성한 유저의 아이디와 투두리스트 내용, 어떤 날짜에 해야하는 일인지가 db에 들어간다.
//
////        유저 정보 저장
//        User user = userRepository.findById(1L).get();
//
////        ToDoListDTO 생성 후 값 세팅하기
//        ToDoListDTO listDTO = new ToDoListDTO();
//        listDTO.setToDoListContent("오늘할일 : 진짜 많음");
//        listDTO.setToDoListSelectDate(LocalDateTime.of(2003, 12, 4, 0, 0));
//
////       ToDoList 엔티티화
//        ToDoList list1 = listDTO.toEntity();
////       ToDoList 엔티티에 유저 set해줌
//        list1.setUser(user);
//
////      alarm 엔티티에 해당 값들을 모두 저장
//        todoListRepository.save(list1);
//    }
//
//    @Test
//    public void findAllTest(){
//        List<ToDoListDTO> lists = jpaQueryFactory.select(new QToDoListDTO(
//                toDoList.toDoListId,
//                toDoList.user.userId,
//                toDoList.toDoListContent,
//                toDoList.toDoListSelectDate
//                )).from(toDoList).fetch();
//
//        log.info("------------------------------------------------------------");
//        lists.stream().map(ToDoListDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//
//    }
//
//    @Test
//    public void findById(){
//        List<ToDoListDTO> lists = jpaQueryFactory.select(new QToDoListDTO(
//                toDoList.toDoListId,
//                toDoList.user.userId,
//                toDoList.toDoListContent,
//                toDoList.toDoListSelectDate
//        )).from(toDoList).where(toDoList.user.userId.eq(1L)).fetch();
//
//        log.info("------------------------------------------------------------");
//        lists.stream().map(ToDoListDTO::toString).forEach(log::info);
//        log.info("------------------------------------------------------------");
//
//    }
//
//    @Test
//    public void updateTest(){
//
////      업데이트할 내용
//        ToDoListDTO toDoListDTO = new ToDoListDTO();
//        toDoListDTO.setUserId(1L);
//        toDoListDTO.setToDoListSelectDate(LocalDateTime.now());
//        toDoListDTO.setToDoListContent("내용 수정");
//
////      userId를 다시 저장
//        ToDoList toDoList1 = jpaQueryFactory.selectFrom(toDoList).where(toDoList.toDoListId.eq(5L)).fetchOne();
//        toDoList1.setUser(userRepository.findById(toDoListDTO.getUserId()).get());
//
//
////      Update 진행
//        toDoList1.update(toDoListDTO);
//    }
//
//    @Test
//    public void deleteTest(){
//        ToDoList toDoList = todoListRepository.findById(6L).get();
//        todoListRepository.delete(toDoList);
//    }
//
//    @Test
//    public void todoListTesT(){
//        todoListCustomRepository.findAllByToday().stream().map(ToDoListDTO::toString).forEach(log::info);
//    }
//}
