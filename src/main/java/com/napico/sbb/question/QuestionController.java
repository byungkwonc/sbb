package com.napico.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.napico.sbb.answer.AnswerForm;

@RequestMapping("/question")
// QuestionController에 속하는 URL 매핑은 항상 /question 프리픽스로 시작하도록 설정
// QuestionController 클래스명 위에 다음과 같이 @RequestMapping("/question") 애너테이션을 추가하고, 메서드 단위에서는 /question을 생략하고 그 뒷부분만을 적으면 된다.
@RequiredArgsConstructor
// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어 주는 역할을 한다.
// 스프링 부트(Spring Boot)가 내부적으로 QuestionController를 생성할 때 롬복으로 만들어진 생성자에 의해 questionRepository 객체가 자동으로 주입
@Controller
public class QuestionController {

    // 질문 목록과 좐련된 테이터를 조화하기 위해 QuestionRepository 사용
    //private final QuestionRepository questionRepository;
    // 질문 목록과 좐련된 테이터를 조화하기 위해 QuestionService 사용
    private final QuestionService questionService;

    // 질문 목록
    //@GetMapping("/question/list")
    @GetMapping("/list")
    // java code를 직접 브라우저에 return.
    //@ResponseBody
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        // java code를 직접 브라우저에 리턴
        //return "question list";

        // template를 써보자. 템플릿 엔진은 Thymeleaf, Mustache, Groovy, Freemarker, Velocity 등이 있다.

        // QuestionRepository로 조회한 질문 목록 데이터인 questionList를 생성
        //List<Question> questionList = this.questionRepository.findAll();
        // QuestionService로 조회한 질문 목록 데이터인 questionList를 생성
        //List<Question> questionList = this.questionService.getList();
        // QuestionService로 조회한 질문 목록 페이징 데이터인 questionList를 생성
        Page<Question> questionList = this.questionService.getList(page);

        // 질문 목록 데이터(questionList)는 Model 클래스를 사용하여 "questionList"라는 이름으로 템플릿에 전달
        // Model 객체는 자바 클래스(Java class)와 템플릿(template) 간의 연결 고리 역할을 한다.
        // Model 객체에 값을 담아 두면 템플릿에서 그 값을 사용할 수 있다.
        // Model 객체는 따로 생성할 필요 없이 컨트롤러의 메서드에 매개변수로 지정하기만 하면 스프링 부트가 자동으로 Model 객체를 생성한다.
        model.addAttribute("questionList", questionList);

        // 템플릿 파일 이름인 question_list를 리턴
        return "question_list";
    }

    // 질문 상세
    //@GetMapping(value = "/question/detail/{id}")
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 질문 등록하기 (GET : 화면이동)
    // 템플릿의 form 태그에 th:object 속성을 추가했으므로 QuestionController의 GetMapping으로 매핑한 메서드에 매개변수로 QuestionForm 객체를 추가해야 오류가 발생하지 않는다.
    // 왜냐하면 question_form.html은 [질문 등록하기] 버튼을 통해 GET 방식으로 URL이 요청되더라도 th:object에 의해 QuestionForm 객체가 필요하기 때문이다.
    // 이렇게 하면 이제 GET 방식에서도 question_form 템플릿에 QuestionForm 객체가 전달된다.
    // QuestionForm과 같이 매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용할 수 있다.
    @GetMapping(value = "/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    // 질문 등록하기 (POST : 데이터 등록, 메서드 오버로딩
    @PostMapping("/create")
    //public String questionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
    //    // 질문 저장 로직
    //    this.questionService.create(subject, content);
    //    return "redirect:/question/list";
    //}

    // questionCreate 메서드의 매개변수를 subject, content 대신 QuestionForm 객체로 변경했다.
    // subject, content 항목을 지닌 폼이 전송되면 QuestionForm의 subject, content 속성이 자동으로 바인딩된다. 이렇게 이름이 동일하면 함께 연결되어 묶이는 것이 바로 폼의 바인딩 기능이다.
    // @Valid 애너테이션을 적용하면 QuestionForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작한다. 그리고 이어지는 BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체이다.
    // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 두 매개변수의 위치가 정확하지 않다면 @Valid만 적용되어 입력값 검증 실패 시 400 오류가 발생한다.
    // questionCreate 메서드는 bindResult.hasErrors()를 호출하여 오류가 있는 경우에는 다시 제목과 내용을 작성하는 화면으로 돌아가도록 했고, 오류가 없을 경우에만 질문이 등록되도록 만들었다.
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        // 질문 저장 로직
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
