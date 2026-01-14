package jade.product.shortifycrawler.global.notify;

import com.google.gson.JsonObject;
import okhttp3.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DiscordNotifier {

    @Value("${discord.webhook-url}")
    private String webhookUrl;

    private final OkHttpClient client = new OkHttpClient();

    public void send(String message) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("content", message);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(webhookUrl)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.error("[DISCORD] notify failed: code={}", response.code());
                }
            }

        } catch (Exception e) {
            log.error("[DISCORD] notify exception", e);
        }
    }
}
