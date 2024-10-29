package com.napico.sbb.question;

import java.util.List;

import com.napico.sbb.question.Question;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 페이징 : Pageable 객체를 입력 받아 Page<Question> 타입 객체를 리턴 하는 findAll 메서드
    Page<Question> findAll(Pageable pageable);

    // 페이징 : 검색 쿼리와 Pageable 객체를 입력 받아 Page<Question> 타입 객체를 리턴 하는 findAll 메서드
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    // 페이징 : 직접 쿼리를 작성할 경우 테이블 기준이 아닌 엔티티 기준으로 작성. 조인은 컬럼명 대신 엔티티의 속성명을 사용
    // Spring Data JPA : JPQL - @Query에 매개변수는 @Param애너테이션을 사용해야한다. 문자열은 @Query내에서 :kw로 참조된다.
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "left outer join Category c on q.category=c "
            + "where "
            + "c.id = :categoryId "
            + "and ("
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ) ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable, @Param("categoryId") String categoryId);
}
