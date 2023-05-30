package com.example.basic.service;

import com.example.basic.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
//    상품 조회
    public List<Product> getList(Pageable pageable);

//    평균 가격보다 낮은 상품 가격 2배 인상
    public void increasePrices();
}
