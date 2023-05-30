package com.example.advanced.entity.hospital;

import com.example.advanced.entity.veterinary.Pet;
import com.example.advanced.repository.hospital.PetDAO;
import com.example.advanced.type.GenderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Random;

@SpringBootTest
@Slf4j
@Rollback(false)
@Transactional
public class HospitalTests {
    @Autowired
    private PetDAO petDAO;

    @Test
    public void saveTest(){
        String[] arDisease = {"감기","배탈","방광염","설사", "피부병"};

        for (int i=0; i<10; i++){
            Owner2 owner2 = new Owner2();
            Pet2 pet2 = new Pet2();

            owner2.setOwnerName("정세인");
            owner2.setOwnerPhone("01012341234" + i);

            pet2.setPetName("뽀삐" + (i+1));
            pet2.setPetGENDER(GenderType.MALE);
            pet2.setDisease(arDisease[new Random().nextInt(arDisease.length)]);
            pet2.setOwner2(owner2);

            petDAO.save(pet2);
        }
    }

    @Test
    public void findById(){
//        petDAO.findById(1L).map(Pet2::toString).ifPresent(log::info);

//        LAZY의 경우 연관 참조중인 엔티티를 원본이 아닌 프록시(대리인)로 받아온다.
//        이 때 참조중인 엔티티를 사용하는 순간 SELCT문이 실행되며,
//        사용하지 않을 경우 프록시로만 존재하기 때문에 SELECT문은 처음부터 실행되지 않는다.
//      fetch join을 사용하면 처음부터 참조중인 엔티티에 원본 객체를 담아놓기 때문에
//        사용할 때 마다 SELECT문이 실행되지 않는다.

        // fetch join
        petDAO.findById(1L).ifPresent(pet2 -> log.info(pet2.getOwner2().getClass().getName()));
    }


    @Test
    public void findAll(){
        petDAO.findAll().stream().map(Pet2::toString).forEach(log::info);
    }


}
