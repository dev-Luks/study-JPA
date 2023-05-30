package com.example.advanced.entity.member;

import com.example.advanced.audit.Period;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_MEMBER")
public class Member extends Period {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true) @NotNull
    private String memberId;
    @NotNull private String memberPassword;
    @Column(unique = true) @NotNull
    private String memberEmail;
    @Embedded
    private MemberAdress memberAdress;

}
