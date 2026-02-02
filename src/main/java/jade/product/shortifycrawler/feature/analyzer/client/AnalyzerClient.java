package jade.product.shortifycrawler.feature.analyzer.client;

import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupRequestDto;
import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupResponseDto;

import java.util.List;

public interface AnalyzerClient {

    List<AnalyzerDedupResponseDto> dedupBatch(
            List<AnalyzerDedupRequestDto> requests
    );
}
