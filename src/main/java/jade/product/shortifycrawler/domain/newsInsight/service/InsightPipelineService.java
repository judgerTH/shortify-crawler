package jade.product.shortifycrawler.domain.newsInsight.service;

import jade.product.shortifycrawler.domain.article.entity.ArticleSummary;
import jade.product.shortifycrawler.domain.article.repository.ArticleSummaryRepository;
import jade.product.shortifycrawler.domain.newsInsight.entity.NewsInsight;
import jade.product.shortifycrawler.domain.newsInsight.repository.NewsInsightRepository;
import jade.product.shortifycrawler.global.llm.GeminiClient;
import jade.product.shortifycrawler.global.llm.InsightParser;
import jade.product.shortifycrawler.global.llm.InsightPromptBuilder;
import jade.product.shortifycrawler.feature.insight.dto.NewsInsightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightPipelineService {

    private final ArticleSummaryRepository summaryRepo;
    private final NewsInsightRepository insightRepo;
    private final GeminiClient geminiClient;
    private final InsightPromptBuilder promptBuilder;

    @Transactional
    public void generateTodayInsight() throws Exception {

        // 1. 오늘 요약된 기사들 조회
        List<ArticleSummary> summaries =
                summaryRepo.findTop20ByOrderByIdDesc();

        if (summaries.isEmpty()) {
            return;
        }

        // 2. 요약 내용 합치기
        String combinedSummaries = summaries.stream()
                .map(ArticleSummary::getSummaryContent)
                .collect(Collectors.joining("\n\n"));

        // 3. 프롬프트 생성
        String prompt = promptBuilder.build(combinedSummaries);

        // 4. LLM 호출
        String response = geminiClient.generate(prompt);

        // 5. 파싱
        NewsInsightResponse parsed = InsightParser.parse(response);

        // 6. 저장
        NewsInsight insight = NewsInsight.create(
                parsed.tension(),
                parsed.positivity(),
                parsed.stability(),
                parsed.summary()
        );

        insightRepo.save(insight);
    }
}
