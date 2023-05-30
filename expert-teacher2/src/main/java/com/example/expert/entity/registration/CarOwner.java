package com.example.expert.entity.registration;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_CAR_OWNER")
public class CarOwner {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String ownerName;
    @NotNull private Integer ownerAge;
    @Embedded
    OwnerAddress ownerAddress;
    @Enumerated(EnumType.STRING)
    private CarOwnerStatusType carOwnerStatus; // 삭제 대신 상태 변경으로 설계

//    N:N관계는 복잡하고 신경쓸 부분이 많기 때문에 실무에서 지양한다.
//    중간 테이블의 유지보수가 N:N관계에서는 불가능하기때문에
//    직접 중간 테이블을 엔티티로 제작하여 N:1 양방향 관계로 풀어내야 한다.
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "carOwners")
//    private List<Car> cars = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carOwner", cascade = CascadeType.PERSIST)
    List<CarRegistration> carRegistrations = new ArrayList<>();

}














