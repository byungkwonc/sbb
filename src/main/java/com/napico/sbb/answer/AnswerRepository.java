package com.napico.sbb.answer;

import com.napico.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 인터페이스를 리포지토리로 만들기위해 JpaRepository 인터페이스를 상속 한다
    // JpaRepository는 JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 내장하고 있어 데이터 관리 작업을 좀 더 편하게 처리할 수 있다.
    // Answer 엔티티로 리포지토리를 생성. Answer 엔티티 기본키가 Integer (id)
// QueryDsl을 사용하기위해 AnswerRepositoryQuerydsl  인터페이스를 상속 한다
    // QueryDsl용으로 별도의 인터페이스를 만들지 않고 구현체를 만들어 AnswerRepository에서 사용할 수 있게 한다.
public interface AnswerRepository extends JpaRepository<Answer, Integer>, AnswerRepositoryQuerydsl {
    Page<Answer> findByQuestion(Question question, Pageable pageable);

    List<Answer> findCurrentAnswer(String username, Pageable pageable);
}

