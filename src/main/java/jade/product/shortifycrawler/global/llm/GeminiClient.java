package jade.product.shortifycrawler.global.llm;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jade.product.shortifycrawler.global.exception.*;
import okhttp3.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final GeminiConfig config;

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .writeTimeout(Duration.ofSeconds(20))
            .readTimeout(Duration.ofSeconds(60))
            .callTimeout(Duration.ofSeconds(60))
            .build();

    public String generate(String prompt) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/"
                        + config.getModel()
                        + ":generateContent?key=" + config.getApiKey();

        try {
            Request request = buildRequest(url, prompt);

            try (Response response = httpClient.newCall(request).execute()) {

                String body = response.body() != null ? response.body().string() : "";

                if (!response.isSuccessful()) {
                    handleHttpError(response.code(), body);
                }

                return body;
            }

        } catch (ShortifyException e) {
            throw e; // 의미 있는 예외는 그대로 전파
        } catch (Exception e) {
            throw new GeminiCriticalException("Gemini client unexpected error", e);
        }
    }

    private Request buildRequest(String url, String prompt) {
        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);

        JsonArray parts = new JsonArray();
        parts.add(part);

        JsonObject content = new JsonObject();
        content.addProperty("role", "user");
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject bodyJson = new JsonObject();
        bodyJson.add("contents", contents);

        RequestBody body = RequestBody.create(
                bodyJson.toString(),
                MediaType.parse("application/json")
        );

        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private void handleHttpError(int status, String body) {

        // Gemini 공식 스펙 기준
        if (status == 429 || status == 503) {
            throw new GeminiTemporaryException(
                    "Gemini temporary unavailable (" + status + ")"
            );
        }

        if (status >= 400 && status < 500) {
            throw new GeminiCriticalException(
                    "Gemini client error (" + status + "): " + body
            );
        }

        if (status >= 500) {
            throw new GeminiCriticalException(
                    "Gemini server error (" + status + "): " + body
            );
        }

        throw new GeminiCriticalException("Unknown Gemini error (" + status + ")");
    }
}
