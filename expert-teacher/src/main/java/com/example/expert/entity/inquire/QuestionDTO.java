package com.example.expert.entity.inquire;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String questionTitle;
    private String questionContents;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
//    private int answerCount;

    private List<Answer> answers;

//    public QuestionDTO(Long id, String questionTitle, String questionContents, LocalDateTime createdDate, LocalDateTime updatedDate, int answerCount) {
//        this.id = id;
//        this.questionTitle = questionTitle;
//        this.questionContents = questionContents;
//        this.createdDate = createdDate;
//        this.updatedDate = updatedDate;
//        this.answerCount = answerCount;
//    }
}
