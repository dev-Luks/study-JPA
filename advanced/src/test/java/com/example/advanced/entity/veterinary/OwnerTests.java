package com.example.advanced.entity.veterinary;

import com.example.advanced.repository.veterinary.OwnerDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class OwnerTests {
    @Autowired
    private OwnerDAO ownerDAO;

    @Test
    public void saveTest(){
        Owner owner = new Owner();
        Pet pet1 = new Pet();

        pet1.setName("나비");
        pet1.setGender("수컷");
        pet1.setDisease("골절");

        owner.setName("김욱성");
        owner.setPhone("01012341234");

        owner.getPets().add(pet1);

        ownerDAO.save(owner);

        ownerDAO.findPetById(2L);
    }

    @Test
    public void findByIdTest(){
        ownerDAO.findById(1L).map(Owner::toString).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        ownerDAO.findAll().stream().map(Owner::toString).forEach(log::info);
    }

    @Test
    public void updateTest(){
        ownerDAO.findById(1L).ifPresent(owner -> owner.getPets().get(0).setDisease("감기"));
    }

    @Test
    public void deleteTest(){
        ownerDAO.findById(1L).ifPresent(ownerDAO::delete);
    }

}
