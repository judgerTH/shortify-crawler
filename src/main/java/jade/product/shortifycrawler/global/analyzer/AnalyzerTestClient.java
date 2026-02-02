package jade.product.shortifycrawler.global.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Slf4j
public class AnalyzerTestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void testCall() {
        String url = "http://localhost:8000/analysis/dedup";

        Map<String, Object> body = Map.of(
                "title", "테스트 기사",
                "content", "이것은 크롤러에서 보낸 테스트 본문입니다."
        );

        try {
            Object response =
                    restTemplate.postForObject(url, body, Object.class);

            log.info("[ANALYZER-TEST] response={}", response);

        } catch (Exception e) {
            log.error("[ANALYZER-TEST] failed", e);
        }
    }
}
