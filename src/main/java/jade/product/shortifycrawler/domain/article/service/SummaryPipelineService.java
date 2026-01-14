package jade.product.shortifycrawler.domain.article.service;

import jade.product.shortifycrawler.domain.article.entity.*;
import jade.product.shortifycrawler.domain.article.repository.*;
import jade.product.shortifycrawler.feature.summary.dto.SummaryResult;
import jade.product.shortifycrawler.feature.summary.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummaryPipelineService {

    private final OriginalArticleRepository originalRepo;
    private final ArticleSummaryRepository summaryRepo;
    private final ArticleMetaRepository metaRepo;
    private final ArticleFailLogRepository failLogRepo;
    private final SummaryService summaryService;

    @Transactional
    public void process(ArticleMeta meta) {

        try {
            OriginalArticle original = originalRepo.findByUrl(meta.getUrl())
                    .orElseThrow(() ->
                            new IllegalStateException("OriginalArticle not found: " + meta.getUrl())
                    );

            String response = summaryService.summarize(original.getContent());
            SummaryResult parsed = SummaryResult.fromGeminiResponse(response);

            if (!parsed.isValid()) {
                throw new IllegalStateException("Invalid summary result");
            }

            ArticleSummary summary = ArticleSummary.create(
                    parsed.getTitle(),
                    parsed.getContent(),
                    parsed.getKeywords(),
                    "gemini-2.5-flash",
                    meta
            );

            summaryRepo.save(summary);
            meta.markSummaryDone();

        } catch (Exception e) {
            meta.markSummaryFailed();

            ArticleFailLog log = new ArticleFailLog();
            log.setUrl(meta.getUrl());
            log.setStep("SUMMARY");
            log.setReason(e.getMessage());
            log.setDetail(null);
            failLogRepo.save(log);

            throw e;
        }
    }
}
