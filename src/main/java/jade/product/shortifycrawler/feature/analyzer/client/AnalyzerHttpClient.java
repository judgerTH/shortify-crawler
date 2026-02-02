package jade.product.shortifycrawler.feature.analyzer.client;

import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupRequestDto;
import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnalyzerHttpClient implements AnalyzerClient {

    private final RestTemplate restTemplate;

    private static final String DEDUP_BATCH_URL =
            "http://analyzer:8000/analysis/dedup/batch";

    @Override
    public List<AnalyzerDedupResponseDto> dedupBatch(
            List<AnalyzerDedupRequestDto> requests
    ) {
        return restTemplate.postForObject(
                DEDUP_BATCH_URL,
                requests,
                List.class
        );
    }
}
