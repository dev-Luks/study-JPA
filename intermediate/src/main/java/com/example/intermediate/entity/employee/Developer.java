package com.example.intermediate.entity.employee;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_DEVELOPER")
@Getter @Setter @ToString(callSuper = true)
@DynamicInsert // 필드에 null이 들어가 있다면 INSERT 쿼리에 해당 필드를 제외해준다.
@DynamicUpdate // 필드에 null이 들어가 있다면 UPDATE SET절에 해당 필드를 제외해준다.
@DiscriminatorValue("dev")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Developer extends Employee {
    private Integer developingLevel;
    @ColumnDefault(value = "0") //컬럼의 초기값 설정, @DynamicInsert를 통해 제외된 필드는 설정한 Default 값이 들어간다.
    private Integer projectCount;

    @Builder
    public Developer(String name, LocalDate birth, Integer career, Integer developingLevel, Integer projectCount) {
        super(name, birth, career);
        this.developingLevel = developingLevel;
        this.projectCount = projectCount;
    }
}






















