package com.example.intermediate.repository;

import com.example.intermediate.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartQueryDSL {
}
