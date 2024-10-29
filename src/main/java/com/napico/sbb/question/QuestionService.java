package com.napico.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.napico.sbb.DataNotFoundException;
import com.napico.sbb.answer.Answer;
import com.napico.sbb.user.SiteUser;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어 주는 역할을 한다.
// 스프링 부트(Spring Boot)가 내부적으로 QuestionService를 생성할 때 롬복으로 만들어진 생성자에 의해 questionRepository 객체가 자동으로 주입
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    // 목록 조회
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    // 상세 내용 조회
    public Question getQuestion(Integer id) {
        // 리포지터리로 얻은 Question 객체는 Optional 객체이므로 if~else 문을 통해 isPresent 메서드로 해당 데이터(여기서는 id값)가 존재하는지 검사하는 과정이 필요
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            Question question1 = question.get();
            // 조회수 업데이트 begin
            if (question1.getReadCount() != null) {
                question1.setReadCount(question1.getReadCount() + 1);
            } else {
                question1.setReadCount(1);
            }
            this.questionRepository.save(question1);
            // 조회수 업데이트 end
            return question1;
        } else {    // id값에 해당하는 질문 데이터가 없을 경우에는 예외 클래스인 DataNotFoundException이 실행
            throw new DataNotFoundException("question not found");
        }
    }

    // 질문 등록
    public void create(String subject, String content, Category categoryId, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(author);
        q.setCreateDate(LocalDateTime.now());
        q.setCategory(categoryId);
        this.questionRepository.save(q);
    }

    // 페이징된 목록 조회
    public Page<Question> getList(int page, String kw, String useMethod, String orderby, String categoryId) {
        if (useMethod.equals("jpql")) {
            List<Sort.Order> sorts = new ArrayList<>();
            switch (orderby) {
                case "1": sorts.add(Sort.Order.desc("createDate"));
                    break;
                case "2": sorts.add(Sort.Order.desc("readCount"));
                    break;
            }
            Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
            return this.questionRepository.findAllByKeyword(kw, pageable, categoryId);
        } else {
            Pageable pageable = PageRequest.of(page, 10);
            Specification<Question> spec = search(kw, orderby, categoryId);
            return this.questionRepository.findAll(spec, pageable);
        }
    }

    // 질문 수정
    public void modify(Question q, String subject, String content) {
        q.setSubject(subject);
        q.setContent(content);
        q.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    // 질문 삭제
    public void delete(Question q) {
        this.questionRepository.delete(q);
    }

    // 질문 추천
    public void vote(Question q, SiteUser voter) {
        q.getVoter().add(voter);
        this.questionRepository.save(q);
    }

    // 검색
    private Specification<Question> search(String kw, String orderby, String categoryId) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);   // 중복제거
                Join<Question, SiteUser> u1 = root.join("author", JoinType.LEFT);
                Join<Question, Answer> a = root.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                Join<Question, Category> c = root.join("category", JoinType.LEFT);
                switch (orderby) {
                    case "1":
                        query.orderBy(criteriaBuilder.desc(root.get("createDate")));
                        break;
                    case "2":
                        query.orderBy(criteriaBuilder.desc(root.get("readCount")));
                        break;
                    default:
                        break;
                }
                return  criteriaBuilder.and(
                            criteriaBuilder.or(
                                criteriaBuilder.like(root.get("subject"), "%" + kw + "%"),
                                criteriaBuilder.like(root.get("content"), "%" + kw + "%"),
                                criteriaBuilder.like(a.get("content"), "%" + kw + "%"),
                                criteriaBuilder.like(u1.get("username"), "%" + kw + "%"),
                                criteriaBuilder.like(u2.get("username"), "%" + kw + "%")),
                        criteriaBuilder.equal(c.get("id"), categoryId));
            }
        };
    }
}
