package com.napico.sbb.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수 항목 입니다.")
    private String content;
}
