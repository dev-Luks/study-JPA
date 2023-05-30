package com.example.intermediate.entity.computer;

import com.example.intermediate.repository.computer.ComputerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class ComputerRepositoryTests {
    @Autowired
    private ComputerRepository computerRepository;

    @Test
    public void saveTest(){
        Hardware hardware = Hardware.builder()
                .ram(16)
                .ssd(1024)
                .gpu("RTX4090")
                .processor("i7-12600U")
                .build();

        Computer computer = Computer.builder()
                .brand("SAMSUNG")
                .name("갤럭시 북 프로3")
                .price(2_000_000)
                .releaseDate(LocalDateTime.now())
                .screen(2880)
                .hardware(hardware)
                .build();

        computerRepository.save(computer);
    }

    @Test
    public void findByRamTest(){
        computerRepository.findByRam(16).stream().map(Computer::toString).forEach(log::info);
    }
}














