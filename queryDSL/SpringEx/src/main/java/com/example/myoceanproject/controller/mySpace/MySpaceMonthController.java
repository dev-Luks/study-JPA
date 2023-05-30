//  손호현, 나의공간 MySpaceMonthController
package com.example.myoceanproject.controller.mySpace;

import com.example.myoceanproject.domain.ToDoListDTO;
import com.example.myoceanproject.repository.TodoListCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//viewResolver가 관여하지 않는다.
@RestController
@RequestMapping("/month/*")
@Slf4j
@RequiredArgsConstructor
public class MySpaceMonthController {
    private final MySpaceService mySpaceService;

    //    /month/?
    @PutMapping("{now}")
    public List<ToDoListDTO> getNow(HttpSession session){
        // HttpSession session = (HttpSession)request.getSession();  =>(HttpServletRequest request)
        Long userId = (Long)session.getAttribute("userId");
        return mySpaceService.showAllByToday(userId);
    }

    //    /month/?
    @GetMapping("{month}")
    public List<Object> getMonth(@PathVariable String month, HttpSession session){
        Long userId = (Long)session.getAttribute("userId");

        // String을 LocalDateTime으로 형변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 화면에서 받아온 값(String) month를 LocalDateTime 타입의 time에 저장.
        LocalDateTime time = LocalDate.parse(month,formatter).atStartOfDay();

        // 해당 날짜의 월별 DTO 목록 List
        List<ToDoListDTO> list = mySpaceService.showAllByMonth(time,userId);
        // 해당 List에 대한 getToDoListSelectDate 시간 목록
        List<LocalDateTime> selectTime = list.stream().map(ToDoListDTO::getToDoListSelectDate).collect(Collectors.toList());
        // HashSet 선언, 시간목록 넘겨줌. (중복 제거)
        Set<LocalDateTime> set = new HashSet<>(selectTime);
        // set-> List로
        List<LocalDateTime> finalTime = set.stream().collect(Collectors.toList());
        // 내림차순으로 정렬
        Collections.sort(finalTime, Collections.reverseOrder());

        // onepiece [0]: 월별 DTO 목록 / [1]: 중복제거한 시간 목록
        List<Object> onepiece = List.of(list,finalTime);

        return onepiece;

    }

    //    /month/?
    @PostMapping("{bno}")
    public ToDoListDTO update(@PathVariable Long bno,ToDoListDTO toDoListDTO){
        return mySpaceService.update(toDoListDTO);
    }


    //    /month/?
    @DeleteMapping("{bno}")
    public String delete(@PathVariable Long bno){

        mySpaceService.delete(bno);
        return "success";
    }
    //    /month/?
    @PatchMapping("{now}")
    public String getToday(){
        return mySpaceService.returnToday();
    }


}