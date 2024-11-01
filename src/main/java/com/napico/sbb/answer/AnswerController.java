package com.napico.sbb.answer;

import com.napico.sbb.question.Question;
import com.napico.sbb.question.QuestionService;
import com.napico.sbb.user.SiteUser;
import com.napico.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    // 답변한 사용자 조회를 위해 추가
    private final UserService userService;

    // 답변 등록
        //public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        //    Question question = this.questionService.getQuestion(id);
        //    this.answerService.create(question, content);
        //    return String.format("redirect:/question/detail/%s", id);
        //}

        // createAnswer 메서드의 매개변수를 content 대신 AnswerForm 객체로 변경했다.
        // content 항목을 지닌 폼이 전송되면 AnswerForm의 content 속성이 자동으로 바인딩된다. 이렇게 이름이 동일하면 함께 연결되어 묶이는 것이 바로 폼의 바인딩 기능이다.
        // @Valid 애너테이션을 적용하면 AnswerForm의 @NotEmpty 검증 기능이 동작한다. 그리고 이어지는 BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체이다.
        // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 두 매개변수의 위치가 정확하지 않다면 @Valid만 적용되어 입력값 검증 실패 시 400 오류가 발생한다.
        // createAnswer 메서드는 bindResult.hasErrors()를 호출하여 오류가 있는 경우에는 다시 질문 상세 화면으로 돌아가도록 했고, 오류가 없을 경우에만 답변이 등록되도록 만들었다.
        // Answer 엔티티에 author 속성 추가 후 답변 데이터를 저장할 때 author(글쓴이)도 함께 저장
        // 현재 로그인한 사용자의 정보를 알려면 스프링 시큐리티가 제공하는 Principal 객체를 사용해야 한다. createAnswer 메서드에 Principal 객체를 매개변수로 지정. principal.getName()을 호출하면 현재 로그인한 사용자의 사용자명(사용자ID)을 알 수 있다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(
            Model model,
            @PathVariable("id") Integer id,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult,
            Principal principal,
            @RequestParam(value="answerpage", defaultValue = "1") int page,
            @RequestParam(value="orderby", defaultValue = "1") String orderby,
            RedirectAttributes redirectAttributes) {
        // 질문 ID 가져오기
        Question question = this.questionService.getQuestion(id);
        // 질문 사용자 가져오기
        SiteUser siteUser = this.userService.getUser(principal.getName());
        // 답변 리스트
        Page<Answer> answerList = this.answerService.getList(page-1, question, orderby);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("answerList", answerList);
            model.addAttribute("orderby", orderby);
            return "question_detail";
        }
        Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
        redirectAttributes.addAttribute("page", page);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    // 답변 수정하기 (GET : 화면이동 및 원래값 채워 넣기)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyAnswer(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    // 답변 수정하기 (POST : 데이터 수정)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    // 답변 삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteAnswer(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    // 답변 추천 하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteAnswer(Principal principal, @PathVariable("id") Integer id) {
        // 답변 가져 오기
        Answer answer = this.answerService.getAnswer(id);
        // 추천인 가져 오기
        SiteUser voter = this.userService.getUser(principal.getName());
        // 추천 저장
        this.answerService.vote(answer, voter);
        return String.format("redirect:/question/detail/%s#answer_%S", answer.getQuestion().getId(), answer.getId());
    }
}