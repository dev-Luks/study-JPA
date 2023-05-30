package com.example.intermediate.entity.user;

import com.example.intermediate.repository.user.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class BusinessRepositoryTests {
    @Autowired
    private BusinessRepository businessRepository;

    @Test
    public void saveTest(){
        Business business1 = Business.builder()
                .businessNumber("2314411")
                .address("경기도")
                .name("정지영")
                .userId("jjy123")
                .password("asd1234")
                .build();

        Business business2 = Business.builder()
                .businessNumber("231441111")
                .address("경기도")
                .name("강민구")
                .userId("kmg123")
                .password("asd12345")
                .build();

        businessRepository.saveAll(new ArrayList<>(Arrays.asList(business1, business2)));
    }

    @Test
    public void updateUserIdByAddressTest(){
        businessRepository.updateUserIdByAddress("경기도");
        businessRepository.findAll().stream().map(Business::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
        businessRepository.findById(112L).ifPresent(business -> business.setPassword("8888"));
    }
}























