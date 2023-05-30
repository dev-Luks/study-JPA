package com.example.expert.entity.registration;

import com.example.expert.audit.Period;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @ToString
@Table(name = "TBL_CAR")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String carName;
    @Enumerated(EnumType.STRING)
    @NotNull private CarType carBrand;
    @ColumnDefault(value = "0")
    private Long carPrice;
    @NotNull private LocalDate carReleaseDate;
    @Enumerated(EnumType.STRING)
    private CarStatusType carStatus; // 삭제 대신 상태 변경으로 설계

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<CarOwner> carOwners;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car", cascade = CascadeType.PERSIST)
    List<CarRegistration> carRegistrations = new ArrayList<>();

    @Builder
    public Car(String carName, CarType carBrand, Long carPrice, LocalDate carReleaseDate, CarStatusType carStatus, List<CarRegistration> carRegistrations) {
        this.carName = carName;
        this.carBrand = carBrand;
        this.carPrice = carPrice;
        this.carReleaseDate = carReleaseDate;
        this.carStatus = carStatus;
        this.carRegistrations = carRegistrations;
    }
}
















