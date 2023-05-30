package com.example.advanced.entity.hospital;

import com.example.advanced.repository.hospital.PetRepository;
import com.example.advanced.type.GenderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class PetlRepositoryTests {
    @Autowired
    private PetRepository petRepository;

    @Test
    public void saveTest(){
        String[] arDisease = {"감기", "배탈", "방광염", "설사", "피부병"};
        for (int i=0; i<10; i++){
            Owner owner = new Owner();
            Pet pet = new Pet();

            owner.setOwnerName("정세인");
            owner.setOwnerPhone("0101234123" + i);

            pet.setPetName("뽀삐" + (i+1));
            pet.setPetGender(GenderType.MALE);
            pet.setPetDisease(arDisease[new Random().nextInt(arDisease.length)]);
            pet.setOwner(owner);

            petRepository.save(pet);
        }
    }

    @Test
    public void findAllTest(){
        List<Pet> pets = petRepository.findAll();
        pets.forEach(pet -> log.info(pet.getOwner().toString()));
    }

}
