package jade.product.shortifycrawler.global.llm;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

        try {
            String url =
                    "https://generativelanguage.googleapis.com/v1beta/"
                            + config.getModel()
                            + ":generateContent?key=" + config.getApiKey();

            // parts
            JsonObject part = new JsonObject();
            part.addProperty("text", prompt);

            JsonArray parts = new JsonArray();
            parts.add(part);

            // content
            JsonObject content = new JsonObject();
            content.addProperty("role", "user");
            content.add("parts", parts);

            JsonArray contents = new JsonArray();
            contents.add(content);

            // request body
            JsonObject bodyJson = new JsonObject();
            bodyJson.add("contents", contents);

            RequestBody body = RequestBody.create(
                    bodyJson.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {

                String responseBody = response.body().string();

                if (!response.isSuccessful()) {
                    throw new IllegalStateException(
                            "Gemini API error (" + response.code() + "): " + responseBody
                    );
                }

                return responseBody;
            }

        } catch (Exception e) {
            throw new IllegalStateException("GeminiClient generate failed", e);
        }
    }
}
