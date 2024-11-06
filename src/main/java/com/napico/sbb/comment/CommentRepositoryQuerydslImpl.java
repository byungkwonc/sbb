package com.napico.sbb.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.napico.sbb.comment.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryQuerydslImpl implements CommentRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    // 사용자 프로필 - 최근 댓글
    @Override
    public List<Comment> findCurrentComment (String username, Pageable pageable) {
        return  jpaQueryFactory
                .selectFrom(comment)
                .where(comment.author.username.eq(username))
                .orderBy(comment.createDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }

    // 사용자 프로필 - 전체 댓글수
    @Override
    public Long findCurrentCommentCount(String username) {
        return jpaQueryFactory
                .selectFrom(comment)
                .where(comment.author.username.eq(username))
                .stream().count();
    }
}
