package com.example.basic.domain.entity;

import com.example.basic.type.MemberType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString // Entity에서는 @Data를 사용하기 보다는 직접 골라서 작성하는 것이 좋다.
@Table(name = "TBL_MEMBER")// 테이블명 작성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 객체 생성을 막음과 동시에, Spring에서는 사용할 수 있도록 PROTECTED로 설정한다.
public class Member {
    @Id @GeneratedValue // @Id는 PK로 설정할 필드 위에 작성하고, ORACLE에서 SEQ 자동 증가는 @GeneratedValue를 사용한다.
    private Long id;
    @NotNull // JAVA에서의 Validation(DB와 상관 없음)
    private String memberName;
    @Column(unique = true, nullable = false) // DBMS에서의 NOT NULL 제약조건 추가(JAVA와 상관 없음)
    private String memberEmail;
    private String memberPassword;
    private int memberAge;
    @Enumerated(EnumType.STRING) // ENUM타입으로 필드를 선언하면, 기본 방식이 ORDINAL이므로 STRING으로 항상 직접 바꿔줘야 한다.
    private MemberType memberType;

    @Builder // 초기화 생성자의 모든 매개변수에 값이 들어와야 메모리에 할당된다.
    public Member(String memberName, String memberEmail, String memberPassword, int memberAge, MemberType memberType) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberAge = memberAge;
        this.memberType = memberType;
    }
}
















