package com.napico.sbb.comment;

import com.napico.sbb.question.Question;
import com.napico.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    public Comment create(Question question, SiteUser author, String content) {
        Comment c = new Comment();
        c.setContent(content);
        c.setAuthor(author);
        c.setQuestion(question);
        c.setCreateDate(LocalDateTime.now());
        c = this.commentRepository.save(c);
        return c;
    }

    // 댓글 가져오기
    public Optional<Comment> getComment(Integer id) {
        return this.commentRepository.findById(id);

    }

    // 댓글 수정
    public Comment modify(Comment c, String content) {
        c.setContent(content);
        c.setModifyDate(LocalDateTime.now());
        c = this.commentRepository.save(c);
        return c;
    }

    // 댓글 삭제
    public void delete(Comment c) {
        this.commentRepository.delete(c);
    }
}
