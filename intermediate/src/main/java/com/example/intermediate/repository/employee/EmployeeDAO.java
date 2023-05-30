package com.example.intermediate.repository.employee;

import com.example.intermediate.entity.employee.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    추가
    public Employee save(Employee employee){
        entityManager.persist(employee);
        return employee;
    }

//    조회
    public Optional<Employee> findById(Long id){
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

//    전체 조회
    public List<Employee> findAll(){
        String query = "select e from Employee e";
        return entityManager.createQuery(query, Employee.class).getResultList();
    }

//    삭제
    public void delete(Employee employee){
        entityManager.remove(employee);
    }
}












