package com.example.intermediate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString(exclude = "product")
@Table(name = "TBL_CART")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private Long count;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart(Long count) {
        this.count = count;
    }
}
