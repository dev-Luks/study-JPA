package com.example.expert.entity.board;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class BoardDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String boardTitle;
    private String boardContent;
    private int likeCount;

    public BoardDTO(Long id, String boardTitle, String boardContent, int likeCount) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = likeCount;
    }
}
