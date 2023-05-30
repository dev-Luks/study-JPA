package com.example.intermediate.entity.computer;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Embeddable;

//    Embeddable
//     상속관계가 아닌 필드의 그룹화(모듈화)를 목적으로 사용한다.
//     그룹화된 필드는 따로 사용하지 않고 한 번에 사용하는 목적으로 설계한다.

@Embeddable
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hardware {
    @NotNull private Integer ram;
    @NotNull private Integer ssd; //기본 단위 GB
    @NotNull private String gpu;
    @NotNull private String processor;

    @Builder
    public Hardware(Integer ram, Integer ssd, String gpu, String processor) {
        this.ram = ram;
        this.ssd = ssd;
        this.gpu = gpu;
        this.processor = processor;
    }
}
