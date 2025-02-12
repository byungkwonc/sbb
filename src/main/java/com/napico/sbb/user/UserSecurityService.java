package com.napico.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    // UserSecurityService는 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현(implements)해야 함.
    // 스프링 시큐리티의 UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스.
    // loadUserByUsername 메서드는 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자명으로 SiteUser 객체를 조회하고, 만약 사용자명에 해당하는 데이터가 없을 경우에는 UsernameNotFoundException을 발생
        // 사용자명이 ‘admin’인 경우에는 ADMIN 권한(ROLE_ADMIN)을 부여하고 그 이외의 경우에는 USER 권한(ROLE_USER)을 부여
        // 마지막으로 User 객체를 생성해 반환하는데, 이 객체는 스프링 시큐리티에서 사용하며 User 생성자에는 사용자명, 비밀번호, 권한 리스트가 전달
        // 스프링 시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는지를 검사하는 기능을 내부에 가지고 있다.

        Optional<SiteUser> optionalSiteUser = this.userRepository.findByusername(username);
        if (optionalSiteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = optionalSiteUser.get();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if ("admin".equals(username)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            grantedAuthorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), grantedAuthorityList);
    }
}
