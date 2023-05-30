package com.example.advanced.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_PRODUCT_ORDER")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

}
