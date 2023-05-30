package com.example.intermediate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "TBL_PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String productName;
    private Integer productPrice;
    private Integer productStock;

    public Product(String productName, Integer productPrice, Integer productStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }
}
















