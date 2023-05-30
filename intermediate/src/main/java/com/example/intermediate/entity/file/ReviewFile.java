package com.example.intermediate.entity.file;

import com.example.intermediate.type.FileType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString(callSuper = true)
@Table(name = "TBL_REVIEW_FILE")
@PrimaryKeyJoinColumn(name = "REVIEW_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFile extends File{
//    이미지 검사
    @Enumerated(EnumType.STRING)
    FileType fileType;

    @Builder
    public ReviewFile(String name, String uuid, String filePath, Long fileSize, FileType fileType) {
        super(name, uuid, filePath, fileSize);
        this.fileType = fileType;
    }
}





















