package com.napico.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
    // @Configuration은 이 파일이 스프링의 환경 설정 파일임을 의미하는 애너테이션
@EnableWebSecurity
    // @EnableWebSecurity는 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션 (스프링 시큐리티를 활성화)
    // 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있게 된다.
@EnableMethodSecurity(prePostEnabled = true)
    // @PreAuthorize 애너테이션이 동작할 수 있도록 스프링 시큐리티 설정 수정 (QuestionController, AnswerController)
public class SecurityConfig {
    // 빈(bean)은 스프링에 의해 생성 또는 관리되는 객체를 의미한다. 컨트롤러, 서비스, 리포지터리 등도 모두 빈에 해당한다.
    // @Bean 애너테이션을 통해 자바 코드 내에서 별도로 빈을 정의하고 등록할 수도 있다.

    // 스프링 시큐리티의 세부 설정은 @Bean 애너테이션을 통해 SecurityFilterChain 빈을 생성하여 설정할 수 있다.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 인증되지 않은 모든 페이지의 요청을 허락
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // H2 console의 CSRF 허용
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                // 프레임으로 구성된 URL 요청 시 X-Frame-Options 헤더를 DENY 대신 SAMEORIGIN으로 설정
            .headers((headers) -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 로그인 페이지의 URL은 /user/login이고, 로그인 성공시 루트(/)로 이동
            .formLogin((formLogin) -> formLogin
                .loginPage("/user/login")
                .defaultSuccessUrl("/"))
                // 로그아웃 시 생성된 사용자 세션 삭제 후 루트(/) 이동
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))
        ;
        return http.build();
    }

    // PasswordEncoder는 BCryptPasswordEncoder의 인터페이스
    // PasswordEncoder 빈을 만드는 가장 쉬운 방법은 @Bean 메서드를 새로 추가
    // BcryptPasswordEncoder 객체를 직접 생성하여 사용하지 않고 빈으로 등록한 Password Encoder 객체를 주입받아 사용할 수 있음
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager는 스프링 시큐리티의 인증을 처리
    // AuthenticationManager는 사용자 인증 시 UserSecurityService와 PasswordEncoder를 내부적으로 사용하여 인증과 권한 부여 프로세스를 처리
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
