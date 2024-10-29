package com.napico.sbb;

import com.napico.sbb.question.Category;
import com.napico.sbb.question.CategoryService;
import com.napico.sbb.question.QuestionService;
import com.napico.sbb.user.SiteUser;
import com.napico.sbb.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SbbApplicationDataTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("대량 입력 [%03d]", i);
            String content = "내용";
            Category categoryId = this.categoryService.getList().get(1);
            SiteUser author = this.userService.getUser("napico");
            this.questionService.create(subject, content, categoryId, author);
        }
    }
}
