package com.example.advanced.entity.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Setter @Getter @ToString
public class MemberAddress {
    private String memberAddress;
    private String memberAddressDetail;
    private String memberPostcode;
}
