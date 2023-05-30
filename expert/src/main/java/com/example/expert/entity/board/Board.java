package com.example.expert.entity.board;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString(exclude = "likes")
@Table(name = "TBL_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
//    id를 HashCode로 설정한다.
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;
    @NotNull private String boardTitle;
    @NotNull private String boardContent;

    @OneToMany(
                fetch = FetchType.LAZY,
                mappedBy = "board",
                cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
                orphanRemoval = true //컬렉션으로 삭제된 객체들까지 전부 감지하도록 설정
            )
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Board(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void addLike(Like like){
        this.likes.add(like);
        if(like.getBoard() != this){
            like.setBoard(this);
        }
    }
}








