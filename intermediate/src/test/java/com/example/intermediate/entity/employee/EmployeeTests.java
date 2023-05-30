package com.example.intermediate.entity.employee;

import com.example.intermediate.entity.employee.Developer;
import com.example.intermediate.entity.employee.Employee;
import com.example.intermediate.entity.employee.Planner;
import com.example.intermediate.repository.employee.EmployeeDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class EmployeeTests {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Test
    public void saveTest(){
        Developer developer = new Developer();
        Planner planner = new Planner();

        developer.setName("한동석");
        developer.setBirth(LocalDate.of(2000, 12, 04));
        developer.setCareer(7);
        developer.setDevelopingLevel(3);
        developer.setProjectCount(20);

        planner.setName("홍길동");
        planner.setBirth(LocalDate.of(1989, 04, 15));
        planner.setCareer(20);
        planner.setOa_level(5);
        planner.setClientCount(5000);

        employeeDAO.save(developer);
        employeeDAO.save(planner);
    }

    @Test
    public void findByIdTest(){
        employeeDAO.findById(1L).ifPresent(employee -> log.info(employee.toString()));
    }

    @Test
    public void findAllTest(){
        assertThat(employeeDAO.findAll().size()).isEqualTo(1);
    }

    @Test
    public void deleteTest(){
        employeeDAO.findById(1L).ifPresent(employeeDAO::delete);
    }

    @Test
    public void updateTest(){
        employeeDAO.findById(2L).ifPresent(employee -> employee.setName("이순신"));
    }

}



















