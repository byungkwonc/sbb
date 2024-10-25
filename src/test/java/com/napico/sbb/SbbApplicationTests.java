package com.napico.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.napico.sbb.answer.Answer;
import com.napico.sbb.answer.AnswerRepository;
import com.napico.sbb.question.Question;
import com.napico.sbb.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // SbbApplicationTests 클래스가 스프링 부트의 테스트 클래스임을 의미
class SbbApplicationTests {
    /**
     * 질문 엔티티의 데이터를 생성할 때 리포지터리(QuestionRepository)가 필요 하므로 @Autowired 애너테이션을 통해 스프링의 ‘의존성 주입(DI)’이라는 기능을 사용하여 QuestionRepository의 객체를 주입
     * questionRepository 변수는 선언만 되어 있고 그 값이 비어 있다. 하지만 @Autowired 애너테이션을 해당 변수에 적용하면 스프링 부트가 questionRepository 객체를 자동으로 만들어 주입한다.
     * 스프링의 의존성 주입(DI, Dependency Injection)이란 스프링이 객체를 대신 생성하여 주입하는 기법이다.
     * 객체를 주입하는 방식에는 @Autowired 애너테이션을 사용하는 것 외에 Setter 메서드 또는 생성자를 사용하는 방식이 있다. 순환 참조 문제와 같은 이유로 개발 시 @Autowired보다는 생성자를 통한 객체 주입 방식을 권장한다.
     * 하지만 테스트 코드의 경우 JUnit이 생성자를 통한 객체 주입을 지원하지 않으므로 테스트 코드 작성 시에만 @Autowired를 사용하고 실제 코드 작성 시에는 생성자를 통한 객체 주입 방식을 권장한다.
     */

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    /**
     * SbbApplicationTests 클래스를 JUnit으로 실행하면 @Test 애너테이션이 붙은 메서드가 실행
     */
    @Transactional
    @Test
    @DisplayName("질문데이터 생성하기")
    void testQuestionSave() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 조회하기 : findAll")
    void testQuestionFindAll() {
        // 응답 결과가 여러 건인 경우에는 리포지터리 메서드의 리턴 타입을 Question이 아닌 List<Question>으로 작성
        List<Question> questionList = this.questionRepository.findAll();
        //assertEquals(1807, questionList.size());

        Question question = questionList.get(0);
        assertEquals("스프링부트 모델 질문입니다.", question.getSubject());
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 조회하기 : findById")
    void testQuestionFindById() {
        // findById의 리턴 타입은 Question이 아닌 Optional임. findById로 호출한 값이 존재할 수도 있고, 존재하지 않을 수도 있어서 리턴 타입으로 Optional이 사용된 것
        // Optional은 그 값을 처리하기 위한(null값을 유연하게 처리하기 위한) 클래스로, isPresent() 메서드로 값이 존재하는지 확인할 수 있다.
        // isPresent()를 통해 값이 존재한다는 것을 확인했다면, get() 메서드를 통해 실제 Question 객체의 값을 얻는다
        Optional<Question> questionOptional = this.questionRepository.findById(2);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            assertEquals("스프링부트 모델 질문입니다.", question.getSubject());
        }
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 조회하기 : findBySubject")
    void testQuestionFindBySubject() {
        // 리포지토리(QuestionRepository)에 커스텀 정의된 메소드 사용 테스트
        Question question = this.questionRepository.findBySubject("스프링부트 모델 질문입니다.");
        assertEquals(2, question.getId());
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 조회하기 : findBySubjectAndContent")
    void testQuestionFindBySubjectAndContent() {
        // 리포지토리(QuestionRepository)에 커스텀 정의된 메소드 사용 테스트
        Question question = this.questionRepository.findBySubjectAndContent("스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?");
        assertEquals(2, question.getId());
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 조회하기 : findBySubjectLike")
    void testQuestionFindBySubjectLike() {
        List<Question> questionList = this.questionRepository.findBySubjectLike("스프링부트%");
        Question question = questionList.get(0);
        assertEquals("스프링부트 모델 질문입니다.", question.getSubject());
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 수정하기")
    void testQuestionModify() {
        Optional<Question> questionOptional = this.questionRepository.findById(5);
        assertTrue(questionOptional.isPresent()); // false 일 경우 오류 발생  및 테스트 종료
        Question question = questionOptional.get();
        question.setSubject("질문 입니다. (수정)");
        this.questionRepository.save(question);
    }

    @Transactional
    @Test
    @DisplayName("질문데이터 삭제하기")
    void testQuestionDelete() {
        //assertEquals(1807, this.questionRepository.count());
        Optional<Question> questionOptional = this.questionRepository.findById(1211);
        assertTrue(questionOptional.isPresent());
        Question question = questionOptional.get();
        this.questionRepository.delete(question);
        //assertEquals(1806, this.questionRepository.count());
    }

    @Transactional
    @Test
    @DisplayName("답변데이터 생성하기")
    void testAnswerSave() {
        Optional<Question> questionOptional = this.questionRepository.findById(2);
        assertTrue(questionOptional.isPresent());
        Question question = questionOptional.get();
        Answer answer = new Answer();
        answer.setContent("네 자동으로 생성됩니다.");
        answer.setQuestion(question);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    @Transactional
    @Test
    @DisplayName("답변데이터 조회하기 : findById")
    void testAnswerFindById() {
        Optional<Answer> answerOptional = this.answerRepository.findById(1);
        assertTrue(answerOptional.isPresent());
        Answer answer = answerOptional.get();
        assertEquals(2, answer.getQuestion().getId());
    }

    @Transactional
    @Test
    @DisplayName("질문을 통해 답변 조회하기")
    void testAnswerFindByQuestionId() {
        /**
         * QuestionRepository가 findById 메서드를 통해 Question 객체를 조회하고 나면 DB 세션이 끊어지기 때문이다.
         * 그래서 그 이후에 실행되는 question.getAnswerList() 메서드(Question 객체로부터 answer 리스트를 구하는 메서드)는 세션이 종료되어 오류가 발생한다.
         * answerList는 앞서 question 객체를 조회할 때가 아니라 question.getAnswerList() 메서드를 호출하는 시점에 가져오기 때문에 이와 같이 오류가 발생한 것이다.
         * ***********************************************************
         * 데이터를 필요한 시점에 가져오는 방식을 지연(Lazy) 방식이라고 한다.
         * 이와 반대로 question 객체를 조회할 때 미리 answer 리스트를 모두 가져오는 방식은 즉시(Eager) 방식이라고 한다.
         * @OneToMany, @ManyToOne 애너테이션의 옵션으로 fetch=FetchType.LAZY 또는 fetch=FetchType.EAGER 처럼 가져오는 방식을 설정할 수 있다.
         * 사실 이 문제는 테스트 코드에서만 발생한다. 실제 서버에서 JPA 프로그램들을 실행할 때는 DB 세션이 종료되지 않아 이와 같은 오류가 발생하지 않는다.
         * 테스트 코드를 수행할 때 이런 오류를 방지할 수 있는 가장 간단한 방법은 다음과 같이 @Transactional 애너테이션을 사용하는 것이다.
         * @Transactional 애너테이션을 사용하면 메서드가 종료될 때까지 DB 세션이 유지된다.
         */
        Optional<Question> questionOptional = this.questionRepository.findById(2);
        assertTrue(questionOptional.isPresent());
        Question question = questionOptional.get();

        List<Answer> answerList = question.getAnswerList();
        assertEquals(6, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

}
