package jade.product.shortifycrawler.feature.summary.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

@Getter
public class SummaryResult {

    private final String title;
    private final String content;
    private final String keywords;

    private SummaryResult(String title, String content, String keywords) {
        this.title = title;
        this.content = content;
        this.keywords = keywords;
    }

    /**
     * Gemini JSON 응답 파싱
     */
    public static SummaryResult fromGeminiResponse(String json) {

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        if (!root.has("candidates")
                || root.getAsJsonArray("candidates").isEmpty()) {
            return empty();
        }

        JsonObject candidate = root.getAsJsonArray("candidates")
                .get(0).getAsJsonObject();

        // parts → 모든 텍스트 이어 붙이기
        StringBuilder sb = new StringBuilder();
        if (candidate.has("content")) {
            candidate.getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .forEach(p -> {
                        JsonObject part = p.getAsJsonObject();
                        if (part.has("text")) {
                            sb.append(part.get("text").getAsString())
                                    .append("\n");
                        }
                    });
        }

        String text = sb.toString().trim();
        if (text.isBlank()) {
            return empty();
        }

        return parseText(text);
    }

    /**
     * LLM이 반환한 순수 텍스트 파싱
     */
    private static SummaryResult parseText(String text) {

        String title = "";
        String summary = "";
        String keywords = "";

        String[] lines = text.split("\n");

        for (String raw : lines) {

            // Markdown 제거
            String line = raw.replace("*", "").trim();

            // 제목
            if (line.matches("(?i)^(제목|title)[^:：\\-]*[:：\\-].*")) {
                title = line.replaceAll(
                        "(?i)^(제목|title)[^:：\\-]*[:：\\-]", ""
                ).trim();
                continue;
            }

            // 요약
            if (line.matches("(?i)^(요약|summary)[^:：\\-]*[:：\\-].*")) {
                summary = line.replaceAll(
                        "(?i)^(요약|summary)[^:：\\-]*[:：\\-]", ""
                ).trim();
                continue;
            }

            // 키워드
            if (line.matches("(?i)^(키워드|keywords)[^:：\\-]*[:：\\-].*")) {
                keywords = line.replaceAll(
                        "(?i)^(키워드|keywords)[^:：\\-]*[:：\\-]", ""
                ).trim();
            }
        }

        // fallback 전략
        if (title.isBlank() && lines.length > 0) {
            title = lines[0].trim();
        }

        if (summary.isBlank() && lines.length > 1) {
            summary = lines[1].trim();
        }

        return new SummaryResult(title, summary, keywords);
    }

    private static SummaryResult empty() {
        return new SummaryResult("", "", "");
    }

    /**
     * 파이프라인에서 쓰기 위한 검증 메서드
     */
    public boolean isValid() {
        return !title.isBlank() && !content.isBlank();
    }
}