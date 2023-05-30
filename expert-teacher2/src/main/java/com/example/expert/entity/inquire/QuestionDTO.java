package com.example.expert.entity.inquire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String questionTitle;
    private String questionContents;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int answerCount;
}
