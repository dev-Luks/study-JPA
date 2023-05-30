package com.example.expert.entity.member;

import com.example.expert.entity.embeddable.Address;
import com.example.expert.entity.order.Order;
import com.example.expert.entity.pay.Pay;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_MEMBER")
@Getter @ToString(exclude = {"pays", "orders"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true)
    @NotNull private String memberId;
    @NotNull private String memberPassword;
    @NotNull private String memberName;
    @NotNull private Integer memberAge;
    @Embedded
    @NotNull private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Pay> pays;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orders;

    public Member(String memberId, String memberPassword, String memberName, Integer memberAge, Address address) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.address = address;
    }
}