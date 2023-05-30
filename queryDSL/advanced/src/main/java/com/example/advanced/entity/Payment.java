package com.example.advanced.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_ORDER")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;
}
