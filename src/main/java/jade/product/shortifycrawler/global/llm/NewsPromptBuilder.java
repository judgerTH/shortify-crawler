package jade.product.shortifycrawler.global.llm;

import org.springframework.stereotype.Component;

@Component
public class NewsPromptBuilder {

    public String build(String content) {

        return """
        아래 뉴스 내용을 기반으로 정확한 텍스트-only 형식으로 출력해라.

        반드시 다음 규칙을 지켜라:
        1) Markdown(**, ##, -, ``` 등) 절대 사용하지 말 것
        2) 반드시 아래 형식 그대로 출력할 것
        3) 불필요한 설명, 인사말, 머리말 금지

        출력 형식:
        제목: 한 문장
        요약: 3~4문장
        키워드: 콤마로 구분한 핵심 단어 3~6개

        [NEWS]
        %s
        """.formatted(content);
    }
}
