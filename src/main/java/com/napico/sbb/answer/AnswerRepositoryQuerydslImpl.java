package com.napico.sbb.answer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.napico.sbb.answer.QAnswer.answer;

@RequiredArgsConstructor
public class AnswerRepositoryQuerydslImpl implements AnswerRepositoryQuerydsl{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Answer> findCurrentAnswer(String username, Pageable pageable) {
        return  jpaQueryFactory
                .selectFrom(answer)
                .where(answer.author.username.eq(username))
                .orderBy(answer.createDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())   // 시작지점 (offset)
                .limit(pageable.getPageSize())                                      // 페이지당갯수 (limit)
                .fetch();
    }

    @Override
    public Long findCurrentAnswerCount(String username) {
        return  jpaQueryFactory
                .selectFrom(answer)
                .where(answer.author.username.eq(username))
                .stream().count();
    }
}
