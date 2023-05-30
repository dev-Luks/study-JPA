package com.example.advanced.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_MEMBER")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true)
    @NotNull private String memberId;
    @NotNull private String memberPassword;
    @NotNull private String memberName;
    @Embedded
    @NotNull private Address address;
}
