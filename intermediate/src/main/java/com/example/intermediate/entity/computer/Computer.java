package com.example.intermediate.entity.computer;

import com.example.intermediate.audit.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_COMPUTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Computer extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private Integer screen;
    @NotNull private String brand;
    @NotNull private String name;
    @NotNull private Integer price;
    @NotNull private LocalDateTime releaseDate;
    @Embedded
    @NotNull private Hardware hardware;

    @Builder
    public Computer(Integer screen, String brand, String name, Integer price, LocalDateTime releaseDate, Hardware hardware) {
        this.screen = screen;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.releaseDate = releaseDate;
        this.hardware = hardware;
    }
}
