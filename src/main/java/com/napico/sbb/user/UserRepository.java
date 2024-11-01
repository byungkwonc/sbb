package com.napico.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    // 사용자 이름 조회
    Optional<SiteUser> findByusername(String username);

    Optional<SiteUser> findByEmail(String email);
}
