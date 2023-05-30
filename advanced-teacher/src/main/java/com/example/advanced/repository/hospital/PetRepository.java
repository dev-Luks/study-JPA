package com.example.advanced.repository.hospital;

import com.example.advanced.entity.hospital.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @EntityGraph(attributePaths = {"owner"})
    public List<Pet> findAll();

}
