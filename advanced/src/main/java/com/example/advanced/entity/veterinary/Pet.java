package com.example.advanced.entity.veterinary;

import com.example.advanced.audit.Period;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter @ToString
@Table(name = "TBL_PET")
public class Pet extends Period {
    @Id @GeneratedValue
    private Long id;
    @NotNull private String name;
    @NotNull private String gender;
    @NotNull private String disease;

}
