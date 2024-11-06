package com.napico.sbb.question;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface QuestionRepositoryQuerydsl {
    // 사용자 프로필 - 최근 게시글
    List<Question> findCurrentQuestion(String username, Pageable pageable);

    // 사용자 프로필 - 전체 게시글 수
    Long findCurrentQuestionCount(String username);
}

