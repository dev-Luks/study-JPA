package com.example.advanced.entity.member;

import com.example.advanced.audit.Period;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_FILE")
public class File extends Period {
    @Id @GeneratedValue
    @NotNull private Long id;
    @NotNull private String fileName;
    @NotNull private String filePath;
    @NotNull private String fileUuid;
    @NotNull private Long fileSize;


    @ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


}
