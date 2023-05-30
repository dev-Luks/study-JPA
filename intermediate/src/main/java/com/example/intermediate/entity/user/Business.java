package com.example.intermediate.entity.user;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter @ToString(callSuper = true)
@Table(name = "TBL_BUSINESS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Business extends User{
    @NotNull private String businessNumber;

    @Builder
    public Business(String userId, String password, String name, String address, String businessNumber) {
        super(userId, password, name, address);
        this.businessNumber = businessNumber;
    }
}
