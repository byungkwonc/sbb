package com.napico.sbb;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 질문 엔티티를 참조하기 위해 question 속성을 추가
    // 답변을 통해 질문의 제목을 알고 싶다면 answer.getQuestion().getSubject()를 사용해 접근할 수 있다.
    // 하지만 이렇게 question 속성만 추가하면 안 되고 질문 엔티티와 연결된 속성이라는 것을 답변 엔티티에 표시해야 한다.
    // 즉, 다음과 같이 Answer 엔티티의 question 속성에 @ManyToOne 애너테이션을 추가해 질문 엔티티와 연결한다.
    // 게시판 서비스에서는 하나의 질문에 답변은 여러 개가 달릴 수 있다. 따라서 답변은 Many(많은 것)가 되고 질문은 One(하나)이 된다.
    // 즉, @ManyToOne 애너테이션을 사용하면 N:1 관계를 나타낼 수 있다.
    // 이렇게 @ManyToOne 애너테이션을 설정하면 Answer(답변) 엔티티의 question 속성과 Question(질문) 엔티티가 서로 연결된다(실제 데이터베이스에서는 외래키(foreign key) 관계가 생성된다.).
    // @ManyToOne은 부모 자식 관계를 갖는 구조에서 사용한다. 여기서 부모는 Question, 자식은 Answer라고 할 수 있다.
    @ManyToOne
    private Question question;
}
