package com.example.basic.repository;

import com.example.basic.domain.entity.SuperCar;
import com.example.basic.type.SuperCarType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class SuperCarRepositoryTests {
    @Autowired
    private SuperCarRepository superCarRepository;

    @Test
    public void saveTest(){
        SuperCar superCar1 = SuperCar.builder()
                .name("람보르기니")
                .color("노란색")
                .price(200000000000L)
                .type(SuperCarType.LAMBORGHINI)
                .releaseDate(LocalDateTime.now())
                .build();

        SuperCar superCar2 = SuperCar.builder()
                .name("플라잉스퍼")
                .color("파란색")
                .price(300000000000L)
                .type(SuperCarType.BENTLEY)
                .releaseDate(LocalDateTime.now())
                .build();


        SuperCar superCar3 = SuperCar.builder()
                .name("포르토피노")
                .color("흰색")
                .price(200000000000L)
                .type(SuperCarType.FERRARI)
                .releaseDate(LocalDateTime.now())
                .build();

        SuperCar superCar4 = SuperCar.builder()
                .name("그랜저")
                .color("황금색")
                .price(50000000L)
                .type(SuperCarType.HYUNDAI)
                .releaseDate(LocalDateTime.now())
                .build();

        List<SuperCar> superCars = new ArrayList<>(Arrays.asList(superCar1,superCar2,superCar3,superCar4));

        superCarRepository.saveAll(superCars);
    }

    @Test
    public void findByIdTest(){
        superCarRepository.findById(417L).ifPresent(superCar -> log.info(superCar.getName()));
    }

    


}
