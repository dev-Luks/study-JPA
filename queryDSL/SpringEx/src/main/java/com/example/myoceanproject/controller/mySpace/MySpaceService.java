//  손호현, 나의공간 MySpaceService
package com.example.myoceanproject.controller.mySpace;

import com.example.myoceanproject.domain.ToDoListDTO;
import com.example.myoceanproject.entity.ToDoList;
import com.example.myoceanproject.repository.TodoListCustomRepository;
import com.example.myoceanproject.repository.TodoListRepository;
import com.example.myoceanproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MySpaceService {
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final TodoListCustomRepository todoListCustomRepository;

//     현재 시간을 지정형식으로 변환하여 리턴
    public String returnToday(){
        // String 날짜를 LocalDateTime타입의 날짜로 바꾸는 과정
        // 패턴을 정해준다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 현재기준을 2022-12-01 지정형식에 맞춰 반환
        return formatter.format(LocalDateTime.now());
    }

//     투두리스트 등록
    public void post(String toDoListContent, String toDoListSelectDate, Long userId){
        ToDoListDTO toDoListDTO = new ToDoListDTO();

        // 회원가입된 유저 찾기
        // HttpSession session = (HttpSession)request.getSession();
        // session.getAttribute("loginUser");

        // String 날짜를 LocalDateTime타입의 날짜로 바꾸는 과정
        // 패턴을 정해준다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDateTime 타입에 (String날짜와 패턴)을 넘겨준다.
        LocalDateTime time = LocalDate.parse(toDoListSelectDate,formatter).atStartOfDay();

        toDoListDTO.setToDoListSelectDate(time);
        toDoListDTO.setToDoListContent(toDoListContent);

        ToDoList toDoList = toDoListDTO.toEntity();
        toDoList.setUser(userRepository.findById(userId).get());

        todoListRepository.save(toDoList);
    }


    //    전체 목록 출력
    public List<ToDoListDTO> showAll(Long userId){
        return todoListCustomRepository.findAllTodoList(userId);
    }

//    오늘 날짜의 투두리스트 목록 출력
    public List<ToDoListDTO> showAllByToday(Long userId){
        return todoListCustomRepository.findAllByToday(userId);
    }

//    선택한 월의 투두리스트 목록 출력
    public List<ToDoListDTO> showAllByMonth(LocalDateTime time,Long userId) {
        return todoListCustomRepository.findAllByMonth(time,userId);
    }

//    투두리스트 수정
    @Transactional
    public ToDoListDTO update(ToDoListDTO toDoListDTO){
        Long id = toDoListDTO.getToDoListId();
        ToDoList toDoList = todoListRepository.findById(id).get();
        toDoList.update(toDoListDTO);

        return todoListCustomRepository.findById(id);
    }

//    투두리스트 삭제
    public void delete(Long id){
        todoListRepository.deleteById(id);
    }

}