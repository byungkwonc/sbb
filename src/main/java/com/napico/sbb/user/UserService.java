package com.napico.sbb.user;

import com.napico.sbb.DataNotFoundException;
import com.napico.sbb.MailException;
import com.napico.sbb.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void modifyPassword(String email, String rootUrl) throws MailException {
        String tempPassword = commonUtil.createTempPassword();
        SiteUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("해당 이메일의 사용자가 없습니다."));
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        mailService.sendMail(email, tempPassword, rootUrl);
    }
}
