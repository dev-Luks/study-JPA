package com.example.expert.entity.registration;

import com.example.expert.audit.Period;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_CAR_REGISTRATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarRegistration extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarOwner carOwner;

    @Builder
    public CarRegistration(Car car, CarOwner carOwner) {
        this.car = car;
        this.carOwner = carOwner;
    }
}


















