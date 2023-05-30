package com.example.advanced.entity.hospital;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_OWNER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {
    @Id @GeneratedValue
    private Long id;
    @NotNull private String ownerName;
    @Column(unique = true)
    @NotNull private String ownerPhone;

    @Builder
    public Owner(String ownerName, String ownerPhone) {
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }
}













