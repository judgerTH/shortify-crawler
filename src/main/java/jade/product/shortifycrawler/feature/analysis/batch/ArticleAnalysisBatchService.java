package jade.product.shortifycrawler.feature.analysis.batch;

import jade.product.shortifycrawler.feature.analysis.service.ArticleAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleAnalysisBatchService {

    private final ArticleAnalysisService analysisService;

    /**
     * 특정 시간대 기사 분석 배치
     */
    public void run(LocalDateTime from, LocalDateTime to) {

        log.info("[ANALYSIS-BATCH] start from={} to={}", from, to);

        int analyzedCount = analysisService.analyzeCollectedArticles(from, to);

        log.info("[ANALYSIS-BATCH] end analyzedCount={}", analyzedCount);
    }
}
