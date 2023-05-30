package com.example.expert.entity.inquire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
public class QuestionDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String questionTitle;
    private String questionContents;
    private int answerCount;
}
