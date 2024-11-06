package com.napico.sbb.answer;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerRepositoryQuerydsl {
    // 사용자 프로필 - 최근 답변
    List<Answer> findCurrentAnswer(String username, Pageable pageable);

    // 사용자 프로필 - 전체 답변 수
    Long findCurrentAnswerCount(String username);
}
