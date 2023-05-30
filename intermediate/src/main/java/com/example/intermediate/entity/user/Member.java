package com.example.intermediate.entity.user;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter @ToString(callSuper = true)
@Table(name = "TBL_MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User{
    @NotNull private String socialSecurityNumber;

    @Builder
    public Member(String userId, String password, String name, String address, String socialSecurityNumber) {
        super(userId, password, name, address);
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
