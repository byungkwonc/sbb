package com.napico.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.napico.sbb.answer.Answer;
import com.napico.sbb.comment.Comment;
import com.napico.sbb.user.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    // database 테이블에서는 모두 소문자로 변경되고 카멜 케이스는 언더바로 구분된다. create_date, modify_date

    // Answer 객체들로 구성된 answerList를 Question 엔티티의 속성으로 추가하고 @OneToMany 애너테이션을 설정했다.
    // 이제 질문에서 답변을 참조하려면 question.getAnswerList()를 호출하면 된다.
    // @OneToMany 애너테이션에 사용된 mappedBy는 참조 엔티티의 속성명을 정의한다. 즉, Answer 엔티티에서 Question 엔티티를 참조한 속성인 question을 mappedBy에 전달해야 한다.
    // 게시판 서비스에서는 질문 하나에 답변이 여러 개 작성될 수 있다. 게시판 서비스에서 질문을 삭제하면 그에 달린 답변들도 모두 삭제되도록 cascade = CascadeType.REMOVE를 사용했다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    // 작성자 1명이 여러개 질문을 할 수 있다.
    @ManyToOne
    private SiteUser author;

    // 추천인. 하나의 질문에 여러 사람이 추천할 수 있고 한 사람이 여러 개의 질문을 추천할 수 있다.
    // Set 자료형으로 작성한 이유는 voter 속성값이 서로 중복되지 않도록 하기 위해서이다. List 자료형과 달리 여기서는 Set 자료형이 voter 속성을 관리하는데 효율적
    // QUESTION_VOTER 테이블생성됨. QUESTION_ID, VOTER_ID
    @ManyToMany
    Set<SiteUser> voter;

    // 질문에 대한 댓글
    @OneToMany(mappedBy = "question")
    private List<Comment> commentList;
}
