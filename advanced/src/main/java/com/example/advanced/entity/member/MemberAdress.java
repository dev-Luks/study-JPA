package com.example.advanced.entity.member;

import javax.persistence.Embeddable;

@Embeddable
public class MemberAdress {
    private String memberAddress;
    private String memberAddressDetail;
    private String MemberPostcode;
}
