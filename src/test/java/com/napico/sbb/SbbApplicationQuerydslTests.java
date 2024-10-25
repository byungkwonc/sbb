package com.napico.sbb;

import com.napico.sbb.question.Question;
import com.napico.sbb.question.QQuestion;
import com.napico.sbb.question.QuestionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class SbbApplicationQuerydslTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("jpa vs querydsl")
    void contextLoads() {
        // jpa
        Optional<Question> oq = this.questionRepository.findById(2);
        Question q = oq.get();
        //em.persist(q);

        // querydsl
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QQuestion qQuestion = QQuestion.question;
        Question result = queryFactory
                .selectFrom(qQuestion)
                .where(qQuestion.id.eq(2))
                .fetchOne();

        // 검증
        assertThat(result).isEqualTo(q);
        assertThat(result.getSubject()).isEqualTo(q.getSubject());
    }
}
