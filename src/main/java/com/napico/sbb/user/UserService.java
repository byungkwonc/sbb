package com.napico.sbb.user;

import com.napico.sbb.DataNotFoundException;
import com.napico.sbb.MailException;
import com.napico.sbb.question.Question;
import com.napico.sbb.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final CommonUtil commonUtil;

    // 사용자 생성
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    // 사용자 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found!");
        }
    }

    // 임시 비번 메일 발송
    public void sendTempPassword(String email, String rootUrl) throws MailException {
        String tempPassword = commonUtil.createTempPassword();
        SiteUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("해당 이메일의 사용자가 없습니다."));
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        mailService.sendMail(email, tempPassword, rootUrl);
    }

    // 비번 변경
    public void modifyPassword(SiteUser siteUser, String password) {
        siteUser.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(siteUser);
    }
}
