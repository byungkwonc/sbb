package com.napico.sbb.user;

import com.napico.sbb.DataNotFoundException;
import com.napico.sbb.MailException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.Binding;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입을 위한 템플릿 렌더링
    @GetMapping("/signup")
    public String signup(UserForm userForm) {
        return "signup_form";
    }

    // 회원 가입 진행
    @PostMapping("/signup")
    public String signup(@Valid UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userForm.getPassword1().equals(userForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "두개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            this.userService.create(userForm.getUsername(), userForm.getEmail(), userForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "login_form";
        // 사용자 아이디와 비밀번호로 로그인 할 수 있는 템플릿 : ~/resources/template/login_form.html
        // 스프링 시큐리티의 로그인이 실패할 경우 시큐리티 기능으로 인해 로그인 페이지로 리다이렉트 된다.
        // 이때 페이지 매개변수로 error가 함께 전달된다. 따라서 로그인 페이지의 매게변수로 error가 전달된 경우 (${param.error}) 오류 메세지를 출력
    }

    // 실제 로그인을 진행하는 @PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리하므로 직접 코드를 작성하여 구현할 필요가 없다.
    // SecurityConfig -> filterChain -> http
    //      .formLogin((formLogin) -> formLogin
    //          .loginPage("/user/login")
    //          .defaultSuccessUrl("/"))

    // 임시비번
    @GetMapping("/temp")
    public String sendTempPassword(PasswordForm passwordForm) {
        return "password_form";
    }

    @PostMapping("/temp")
    public String sendTempPassword(@Valid PasswordForm passwordForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "password_form";
        }
        try {
            this.userService.sendTempPassword(passwordForm.getEmail(), request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
        } catch (MailException e) {
            e.printStackTrace();
            bindingResult.reject("fail to send mail", e.getMessage());
            return "password_form";
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            bindingResult.reject("email not found", e.getMessage());
            return "password_form";
        }
        return "redirect:/";
    }

    // 비밀번호 변경 폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modifyPassword(Model model, ModifyPasswordForm modifyPasswordForm, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        model.addAttribute("userid", siteUser.getUsername());
        return "modify_form";
    }

    // 비밀번호 변경 액션
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modifyPassword(Model model, @Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult, Principal principal) {

        SiteUser siteUser = this.userService.getUser(principal.getName());

        if (!modifyPasswordForm.getUserid().equals(principal.getName())) {
            bindingResult.rejectValue("password", "useridInCorrect", "회원정보가 일치하지 않습니다.");
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        }

        if (!passwordEncoder.matches(modifyPasswordForm.getPassword(), siteUser.getPassword())) {
            bindingResult.rejectValue("password", "passwordInCorrect", principal.getName() + "님의" + siteUser.getPassword() + "와 " + modifyPasswordForm.getPassword() + " 패스워드가 일치하지 않습니다.");
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        }

        if (!modifyPasswordForm.getPassword1().equals(modifyPasswordForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "두개의 패스워드가 일치하지 않습니다.");
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        }

        try {
            this.userService.modifyPassword(siteUser, modifyPasswordForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", "이미 등록된 사용자 입니다.");
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", e.getMessage());
            model.addAttribute("userid", siteUser.getUsername());
            return "modify_form";
        }
        return "redirect:/user/logout";
    }
}
