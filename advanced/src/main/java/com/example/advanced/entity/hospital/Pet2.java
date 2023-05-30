package com.example.advanced.entity.hospital;

import com.example.advanced.audit.Period;
import com.example.advanced.type.GenderType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_PET2")
public class Pet2 extends Period {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull private String petName;
    @Enumerated(EnumType.STRING)
    @NotNull private GenderType petGENDER;
    @NotNull private String disease;


//    CascadeType.REMOVE는 로직에 따라 설정해야 하며,
//    잘못 설정 시 참조 엔티티 삭제 후 기존 엔티티까지 삭제하는 이슈가 발생한다.
//
//    fetch는 무조건 LAZY로 설정해라
    @ManyToOne(fetch = FetchType.LAZY ,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="OWNER_ID")
    private Owner2 owner2;

}
