package com.example.intermediate.repository.computer;

import com.example.intermediate.entity.computer.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
    @Query("select c from Computer c where c.hardware.ram = :ram")
    public List<Computer> findByRam(Integer ram);
}
