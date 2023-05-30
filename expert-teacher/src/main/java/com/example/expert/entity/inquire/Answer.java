package com.example.expert.entity.inquire;

import com.example.expert.audit.Period;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString(exclude = "question")
@Table(name = "TBL_ANSWER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String answerContents;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Builder
    public Answer(String answerContents) {
        this.answerContents = answerContents;
    }
}


















