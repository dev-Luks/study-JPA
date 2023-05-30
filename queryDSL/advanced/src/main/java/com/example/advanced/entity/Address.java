package com.example.advanced.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String postcode;
    private String address;
    private String addressDetail;
}
