package com.napico.sbb.comment;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryQuerydsl {
    // 사용자 프로필 - 최근 댓글
    List<Comment> findCurrentComment (String username, Pageable pageable);

    // 사용자 프로필 - 전체 댓글수
    Long findCurrentCommentCount (String username);
}
