package com.example.expert.entity.pay;

import com.example.expert.entity.member.Member;
import com.example.expert.entity.product.Product;
import com.example.expert.entity.order.Order;
import com.example.expert.entity.type.PayStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PAY")
@Getter @ToString(exclude = {"product", "order", "member"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private Long totalPrice;
    @Enumerated(EnumType.STRING)
    @NotNull private PayStatus payStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public Pay(Long totalPrice, PayStatus payStatus) {
        this.totalPrice = totalPrice;
        this.payStatus = payStatus;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
