package com.example.expert.repository.pay;

import com.example.expert.entity.product.Product;
import com.example.expert.entity.product.ProductDTO;

import java.util.List;
import java.util.Optional;

// N:N관계에서 중간 테이블 발생 시,
// 양 쪽 모두 정보가 필요한 경우 중간 테이블 레포지토리에서 작업해야 한다.

public interface PayQueryDsl {
//    상품 별 주문 횟수 조회
    public List<ProductDTO> findCountOfOrder();

//    최다 주문 상품 조회(인기 상품)
    public Optional<ProductDTO> findTop1ProductByOrder();

//    주문 번호의 상품 조회
    public List<Product> findProductByOrderId(Long id);
}
