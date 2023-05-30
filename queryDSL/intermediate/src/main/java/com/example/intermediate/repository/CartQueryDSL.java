package com.example.intermediate.repository;

import com.example.intermediate.entity.Cart;

import java.util.List;

public interface CartQueryDSL {
//    전체 조회
    public List<Cart> findAll_QueryDSL();
}
