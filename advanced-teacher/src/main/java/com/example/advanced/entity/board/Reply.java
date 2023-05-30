package com.example.advanced.entity.board;

import com.example.advanced.audit.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_REPLY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends Period {
    @Id @GeneratedValue
    private Long id;
    @NotNull private String replyContent;

    @Builder
    public Reply(String replyContent) {
        this.replyContent = replyContent;
    }
}





















