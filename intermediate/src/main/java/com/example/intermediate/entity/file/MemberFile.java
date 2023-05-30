package com.example.intermediate.entity.file;

import com.example.intermediate.type.RepresentationalType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString(callSuper = true)
@Table(name = "TBL_MEMBER_FILE")
@PrimaryKeyJoinColumn(name = "MEMBER_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFile extends File {
//    대표 이미지 검사
    @Enumerated(EnumType.STRING)
    private RepresentationalType representationalType;

    @Builder
    public MemberFile(String name, String uuid, String filePath, Long fileSize, RepresentationalType representationalType) {
        super(name, uuid, filePath, fileSize);
        this.representationalType = representationalType;
    }
}
