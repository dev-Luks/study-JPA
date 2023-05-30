package com.example.expert.repository.car;

import com.example.expert.entity.registration.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
