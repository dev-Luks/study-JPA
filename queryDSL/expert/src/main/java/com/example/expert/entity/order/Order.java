package com.example.expert.entity.order;

import com.example.expert.entity.embeddable.Address;
import com.example.expert.entity.pay.Pay;
import com.example.expert.entity.member.Member;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_ORDER")
@Getter @ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Embedded
    @NotNull private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Order(Address address) {
        this.address = address;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
