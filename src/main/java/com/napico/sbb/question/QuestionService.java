package com.napico.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.napico.sbb.DataNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
            return question.get();
        } else {    // id값에 해당하는 질문 데이터가 없을 경우에는 예외 클래스인 DataNotFoundException이 실행
            throw new DataNotFoundException("question not found");
        }
    }

    // 질문 등록
    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    // 페이징된 목록 조회
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
}
