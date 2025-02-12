package com.napico.sbb.answer;

import com.napico.sbb.DataNotFoundException;
import com.napico.sbb.question.Question;
import com.napico.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 답변 생성
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    // 답변 조회
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    // 답변 수정
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    // 답변 삭제
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    // 답변 추천
    public void vote(Answer answer, SiteUser voter) {
        answer.getVoter().add(voter);
        this.answerRepository.save(answer);
    }

    // 답변 리스트
    public Page<Answer> getList(int page, Question question, String orderby) {
        List<Sort.Order> sorts = new ArrayList<>();
        switch (orderby) {
            case "1": sorts.add(Sort.Order.desc("createDate"));
                break;
            case "2": sorts.add(Sort.Order.desc("voter"));
                break;
        }
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.answerRepository.findByQuestion(question, pageable);
    }

    // 사용자 프로필 - 최근 답변
    public List<Answer> getAnswerListByUser(String username, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        return this.answerRepository.findCurrentAnswer(username,pageable);
    }

    // 사용자 프로필 - 전체 답변 수
    public Long getAnswerCountByUser(String username) {
        return this.answerRepository.findCurrentAnswerCount(username);
    }
}
