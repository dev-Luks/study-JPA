package com.example.expert.repository.product;

import com.example.expert.entity.pay.Pay;
import com.example.expert.entity.product.Product;
import com.example.expert.entity.product.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryDsl {
//    상품의 결제 내역
    public List<Pay> findPayByProduct(Long id);

//    상품 검색(상품명, 상품 가격, 상품 재고)
    public Page<Product> findAllWithSearch(ProductSearch productSearch, Pageable pageable);
}
