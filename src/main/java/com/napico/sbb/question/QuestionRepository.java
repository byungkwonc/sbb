package com.napico.sbb.question;

import java.util.List;

import com.napico.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 인터페이스를 리포지토리로 만들기위해 JpaRepository 인터페이스를 상속 한다
// JpaRepository는 JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 내장하고 있어 데이터 관리 작업을 좀 더 편하게 처리할 수 있다.
// Question 엔티티로 리포지토리를 생성. Question 엔티티 기본키가 Integer (id)
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 커스텀 메서드 사용을 위해 인터페이스에 메서드 추가 선언
    // JPA에 리포지터리의 메서드명을 분석하여 쿼리를 만들고 실행하는 기능이 있음
    // findBy + 엔티티의 속성명과 같은 리포지터리의 메서드를 작성하면 입력한 속성의 값으로 데이터를 조회할 수 있음.

    // 제목 검색
    Question findBySubject(String subject);
    // 제목 + 내용 검색
    Question findBySubjectAndContent(String subject, String content);
    // 제묵 LIKE 검색
    List<Question> findBySubjectLike(String subject);

    // 조합할 수 있는 메서드 예시
    // findBySubjectAndContent(String subject, String content)
    // findBySubjectOrContent(String subject, String content)
    // findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate)
    // findByIdLessThan(Integer id)
    // findByIdGreaterThanEqual(Integer id)
    // findBySubjectIn(String[] subjects)
    // findBySubjectOrderByCreateDateAsc(String subject)

    // 페이징 : Pageable 객체를 입력받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드
    Page<Question> findAll(Pageable pageable);
}
