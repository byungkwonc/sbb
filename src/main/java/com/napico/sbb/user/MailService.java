package com.napico.sbb.user;

import com.napico.sbb.MailException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MimeMessage createMail(String to, String pwd, String rootUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("비밀번호 변경");
        mimeMessageHelper.setText(setContext(pwd, rootUrl), true);
        mimeMessageHelper.setFrom(new InternetAddress("napico@naver.com", "나삐꾸"));
        log.info("Succeeded to createMail");

        return mimeMessage;
    }

    public void sendMail (String to, String pwd, String rootUrl) {
        MimeMessage message;
        try {
            message = createMail(to, pwd, rootUrl);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            throw new MailException("이메일 생성 에러");
        }

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new MailException("이메일 전송 에러");
        }
    }

    //thymeleaf를 통한 html 적용
    public String setContext(String pwd, String rootUrl) {
        Context context = new Context();
        context.setVariable("pwd", pwd);
        context.setVariable("rootUrl", rootUrl);
        return templateEngine.process("mail_context", context);
    }
}