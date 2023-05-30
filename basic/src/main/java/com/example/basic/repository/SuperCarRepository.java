package com.example.basic.repository;

import com.example.basic.domain.entity.SuperCar;
import com.example.basic.type.SuperCarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperCarRepository extends JpaRepository<SuperCar, Long> {
    public List<SuperCar> findByName(String name);
    public List<SuperCar> findByNameContaining(String name);
    public List<SuperCar> findByNameStartingWith(String name);
    public List<SuperCar> findByNameEndingWith(String name);
    public List<SuperCar> findTop2ByPriceGreaterThanEqualOrderByPriceDesc(double price);
    public Boolean existsAllByType(Long price);
    public int countAllByType(SuperCarType type);
    public void deleteByPriceGreaterThanEqual(Long price);

    public List<SuperCar> findByColorOrName(String color, String name);
}
