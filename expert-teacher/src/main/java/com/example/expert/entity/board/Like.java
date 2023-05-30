package com.example.expert.entity.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString(exclude = "board")
@Table(name = "TBL_LIKE")
public class Like {
//    좋아요 번호
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}






