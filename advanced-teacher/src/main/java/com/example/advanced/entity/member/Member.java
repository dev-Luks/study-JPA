package com.example.advanced.entity.member;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    private Long id;
    @NotNull @Column(unique = true)
    private String memberId;
    @NotNull private String memberPassword;
    @NotNull @Column(unique = true)
    private String memberEmail;
    @Embedded private MemberAddress memberAddress;

    @Builder
    public Member(String memberId, String memberPassword, String memberEmail, MemberAddress memberAddress) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberAddress = memberAddress;
    }
}

















