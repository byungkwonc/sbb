package com.napico.sbb.answer;

import com.napico.sbb.question.Question;
import com.napico.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
//    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
//        Question question = this.questionService.getQuestion(id);
//        this.answerService.create(question, content);
//        return String.format("redirect:/question/detail/%s", id);
//    }
    // createAnswer 메서드의 매개변수를 content 대신 AnswerForm 객체로 변경했다.
    // content 항목을 지닌 폼이 전송되면 AnswerForm의 content 속성이 자동으로 바인딩된다. 이렇게 이름이 동일하면 함께 연결되어 묶이는 것이 바로 폼의 바인딩 기능이다.
    // @Valid 애너테이션을 적용하면 AnswerForm의 @NotEmpty 검증 기능이 동작한다. 그리고 이어지는 BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체이다.
    // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 두 매개변수의 위치가 정확하지 않다면 @Valid만 적용되어 입력값 검증 실패 시 400 오류가 발생한다.
    // createAnswer 메서드는 bindResult.hasErrors()를 호출하여 오류가 있는 경우에는 다시 질문 상세 화면으로 돌아가도록 했고, 오류가 없을 경우에만 답변이 등록되도록 만들었다.
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
}