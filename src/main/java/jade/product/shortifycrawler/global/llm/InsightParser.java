package jade.product.shortifycrawler.global.llm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.product.shortifycrawler.feature.insight.dto.NewsInsightResponse;

public class InsightParser {

    public static NewsInsightResponse parse(String json) {

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        // Gemini 기본 구조
        JsonObject candidate = root.getAsJsonArray("candidates")
                .get(0).getAsJsonObject();

        String text = candidate
                .getAsJsonObject("content")
                .getAsJsonArray("parts")
                .get(0).getAsJsonObject()
                .get("text")
                .getAsString();

        // 혹시 모를 코드블럭 제거
        text = text.replace("```json", "")
                .replace("```", "")
                .trim();

        JsonObject obj = JsonParser.parseString(text).getAsJsonObject();

        return new NewsInsightResponse(
                obj.get("tension").getAsInt(),
                obj.get("positivity").getAsInt(),
                obj.get("stability").getAsInt(),
                obj.get("summary").getAsString()
        );
    }
}
