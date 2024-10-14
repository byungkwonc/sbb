package com.napico.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어 주는 역할을 한다.
// 스프링 부트(Spring Boot)가 내부적으로 QuestionController를 생성할 때 롬복으로 만들어진 생성자에 의해 questionRepository 객체가 자동으로 주입
@Controller
public class QuestionController {

    // 질문 목록과 좐련된 테이터를 조화하기 위해 QuestionRepository 사용
    private final QuestionRepository questionRepository;
    @GetMapping("/question/list")
    // java code를 직접 브라우저에 return.
    //@ResponseBody
    public String list(Model model) {
        // java code를 직접 브라우저에 리턴
        //return "question list";
        // template를 써보자. 템플릿 엔진은 Thymeleaf, Mustache, Groovy, Freemarker, Velocity 등이 있다.
        // QuestionRepository로 조회한 질문 목록 데이터인 questionList를 생성
        List<Question> questionList = this.questionRepository.findAll();
        // 질문 목록 데이터(questionList)는 Model 클래스를 사용하여 "questionList"라는 이름으로 템플릿에 전달
        // Model 객체는 자바 클래스(Java class)와 템플릿(template) 간의 연결 고리 역할을 한다.
        // Model 객체에 값을 담아 두면 템플릿에서 그 값을 사용할 수 있다.
        // Model 객체는 따로 생성할 필요 없이 컨트롤러의 메서드에 매개변수로 지정하기만 하면 스프링 부트가 자동으로 Model 객체를 생성한다.
        model.addAttribute("questionList", questionList);
        // 템플릿 파일 이름인 question_list를 리턴
        return "question_list";
    }

}
