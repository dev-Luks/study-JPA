package com.example.expert.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String postcode;
    private String address;
    private String addressDetail;
}
