package com.example.expert.entity.inquire;

import com.example.expert.audit.Period;
import com.example.expert.entity.inquire.Answer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_QUESTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends Period {
    @Id @GeneratedValue
    private Long id;
    private String questionTitle;
    private String questionContents;

//    mappedBy
//    단방향 2개로 양방향을 설계했을 경우 서로 FK를 수정 및 추가할 수 있다.
//    그런데, 서로 수정을 하게 되면 양쪽 모두의 FK를 동기화해야 하기 때문에(일관성) 번거롭고 무결성에 위반될 수도 있다.
//    따라서, mappedBy를 사용하여 N쪽의 FK를 연관관계의 주인으로 설정해야 한다.
//    mappedBy에 작성한 필드명은 RDB 진영에서 "_id"를 붙여 FK의 이름으로 사용된다.
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "question")
    private Answer answer;
//    편의 메소드
//    연관관계의 주인 엔티티가 아닌 반대편 엔티티로 FK를 추가하고자 할 때
//    NULL값을 기존 FK값으로 변경하고자 사용한다.
    public void setAnswer(Answer answer) {
//        전달받은 answer에 question객체가 없다면
        if(answer.getQuestion() != this){
//            직접 question객체를 넣어준다.
            answer.setQuestion(this);
        }
        this.answer = answer;
    }

    @Builder
    public Question(String questionTitle, String questionContents, Answer answer) {
        this.questionTitle = questionTitle;
        this.questionContents = questionContents;
        this.answer = answer;
    }
}
























