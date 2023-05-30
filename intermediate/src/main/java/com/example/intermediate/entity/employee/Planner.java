package com.example.intermediate.entity.employee;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_PLANNER")
@Getter @Setter @ToString(callSuper = true)
@DynamicInsert
@DynamicUpdate
@DiscriminatorValue("pln")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planner extends Employee {
    private Integer oa_level;
    @ColumnDefault(value = "0")
    private Integer clientCount;

    @Builder
    public Planner(String name, LocalDate birth, Integer career, Integer oa_level, Integer clientCount) {
        super(name, birth, career);
        this.oa_level = oa_level;
        this.clientCount = clientCount;
    }
}





















