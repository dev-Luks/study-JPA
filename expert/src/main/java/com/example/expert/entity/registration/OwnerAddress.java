package com.example.expert.entity.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @ToString
public class OwnerAddress {
    private String ownerAddress;
    private String ownerAddressDetail;
    private String ownerZipcode;
}
