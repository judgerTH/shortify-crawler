package jade.product.shortifycrawler.global.llm;

import org.springframework.stereotype.Component;

@Component
public class InsightPromptBuilder {

    public String build(String combinedSummaries) {
        return """
        아래는 오늘의 뉴스 요약들이다. 이 뉴스들의 내용을 기반으로 한국 사회의 전반적인 분위기를 분석하라.

        반드시 다음 조건을 지켜라:
        1) tension, positivity, stability는 뉴스 요약을 실제로 반영해 매번 달라야 한다.
        2) 임의로 값이나 요약을 반복하거나 예시 값을 재사용하면 안 된다.
        3) 뉴스 내용을 분석하여 정량적 수치를 산출하라.
        4) 오직 JSON 형식으로만 출력하라.
        5) JSON 외의 설명이나 코드블록 표시( ``` )는 출력하지 말라.

        출력 형식 규칙:
        tension: 0~100 사이의 숫자
        positivity: 0~100 사이의 숫자
        stability: 0~100 사이의 숫자
        summary: 뉴스 기반의 한 문장 요약

        출력 예시 형태:
        {"tension": 0, "positivity": 0, "stability": 0, "summary": ""}

        뉴스 요약:
        %s
        """.formatted(combinedSummaries);
    }
}
