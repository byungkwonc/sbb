package com.napico.sbb.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component              // 스프링 부트가 관리하는 Bean으로 등록. Bean으로 등록된 컴포넌트는 템플릿에서 사용 가능.
public class CommonUtil {
    // 마크다운 텍스트를 HTML문서로 변환하여 리턴
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }


    // 임시 비번 생성
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();
    public String createTempPassword() {
        StringBuilder tmp = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            tmp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return tmp.toString();
    }
}

