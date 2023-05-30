package com.example.expert.entity.product;

import com.example.expert.entity.pay.Pay;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_PRODUCT")
@Getter @ToString(exclude = "pays")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String productName;
    @NotNull private Long productStock;
    @NotNull private Long productPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Pay> pays;

    public Product(String productName, Long productStock, Long productPrice) {
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
    }
}














