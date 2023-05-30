package com.example.basic.repository;

import com.example.basic.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryDSL {
//    상품 목록
    public Page<Product> findAllWithPaging(Pageable pageable);
    
//    평균 가격보다 낮은 상품 가격 2배 인상
    public void updatePrices();
}
