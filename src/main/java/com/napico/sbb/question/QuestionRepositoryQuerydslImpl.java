package com.napico.sbb.question;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.napico.sbb.question.QQuestion.question;

@RequiredArgsConstructor
public class QuestionRepositoryQuerydslImpl implements QuestionRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    // 사용자 프로필 - 최근 게시글
    @Override
    public List<Question> findCurrentQuestion(String username, Pageable pageable) {
        return  jpaQueryFactory
                .selectFrom(question)
                .where(question.author.username.eq(username))
                .orderBy(question.createDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())   // 시작지점 (offset)
                .limit(pageable.getPageSize())                                      // 페이지당갯수 (limit)
                .fetch();
    }

    // 사용자 프로필 - 전체 게시글 수
    @Override
    public Long findCurrentQuestionCount(String username) {
        return  jpaQueryFactory
                .selectFrom(question)
                .where(question.author.username.eq(username))
                .stream().count();
    }
}
