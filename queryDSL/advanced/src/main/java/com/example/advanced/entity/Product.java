package com.example.advanced.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_PRODUCT")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Product {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String productName;
    @ColumnDefault(value = "0")
    private Long productStock;
    @ColumnDefault(value = "0")
    private Long productPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<ProductOrder> productOrders;
}














