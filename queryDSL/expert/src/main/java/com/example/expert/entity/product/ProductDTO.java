package com.example.expert.entity.product;

import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ProductDTO {
    private Long id;
    private String productName;
    private Long productStock;
    private Long productPrice;
    private int orderCount;

    @QueryProjection
    public ProductDTO(Long id, String productName, Long productStock, Long productPrice, int orderCount) {
        this.id = id;
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
        this.orderCount = orderCount;
    }
}
