package com.example.advanced.entity.hospital;

import com.example.advanced.audit.Period;
import com.example.advanced.type.GenderType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_PET")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends Period {
    @Id @GeneratedValue
    private Long id;
    private String petName;
    @Enumerated(EnumType.STRING)
    @NotNull private GenderType petGender;
    @NotNull private String petDisease;

//    CascadeType.REMOVE는 로직에 따라 설정해야 하며,
//    잘못 설정 시 참조 엔티티 삭제 후 기존 엔티티까지 삭제하는 이슈가 발생한다.
//    예) 반려동물 정보를 삭제하면 주인 정보도 삭제된다.
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @Builder
    public Pet(String petName, GenderType petGender, String petDisease, Owner owner) {
        this.petName = petName;
        this.petGender = petGender;
        this.petDisease = petDisease;
        this.owner = owner;
    }
}




























