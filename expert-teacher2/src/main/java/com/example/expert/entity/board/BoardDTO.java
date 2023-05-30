package com.example.expert.entity.board;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class BoardDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String boardTitle;
    private String boardContent;
    private int likeCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /*목록할때 사용한거.*/
    public BoardDTO(Long id, String boardTitle, String boardContent, int likeCount) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = likeCount;
    }
}
