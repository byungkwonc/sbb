package com.napico.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스를 리포지토리로 만들기위해 JpaRepository 인터페이스를 상속 한다
// JpaRepository는 JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 내장하고 있어 데이터 관리 작업을 좀 더 편하게 처리할 수 있다.
// Answer 엔티티로 리포지토리를 생성. Answer 엔티티 기본키가 Integer (id)
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}

