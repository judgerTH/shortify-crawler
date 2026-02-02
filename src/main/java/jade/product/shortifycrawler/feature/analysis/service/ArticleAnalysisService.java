package jade.product.shortifycrawler.feature.analysis.service;

import jade.product.shortifycrawler.domain.article.entity.ArticleMeta;
import jade.product.shortifycrawler.domain.article.entity.ArticleProcessStatus;
import jade.product.shortifycrawler.domain.article.repository.ArticleMetaRepository;
import jade.product.shortifycrawler.feature.analysis.dto.ArticleAnalysisTarget;
import jade.product.shortifycrawler.feature.analyzer.client.AnalyzerClient;
import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupRequestDto;
import jade.product.shortifycrawler.feature.analyzer.dto.AnalyzerDedupResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleAnalysisService {

    private final ArticleMetaRepository articleMetaRepository;
    private final AnalyzerClient analyzerClient;

    public int analyzeCollectedArticles(LocalDateTime from, LocalDateTime to) {

        // 분석 대상 조회
        List<ArticleAnalysisTarget> targets =
                articleMetaRepository.findAnalysisTargets(
                        ArticleProcessStatus.COLLECTED,
                        from,
                        to
                );

        if (targets.isEmpty()) {
            log.info("[ANALYSIS] no collected articles");
            return 0;
        }

        // Analyzer 요청 DTO 변환
        List<AnalyzerDedupRequestDto> requests =
                targets.stream()
                        .map(AnalyzerDedupRequestDto::from)
                        .toList();

        // 배치 분석 호출 (핵심)
        List<AnalyzerDedupResponseDto> results =
                analyzerClient.dedupBatch(requests);

        // 결과를 articleId 기준으로 매핑
        Map<Long, AnalyzerDedupResponseDto> resultMap =
                results.stream()
                        .collect(Collectors.toMap(
                                AnalyzerDedupResponseDto::getArticleId,
                                r -> r
                        ));

        int analyzed = 0;
        int duplicated = 0;

        // 상태 반영
        for (ArticleAnalysisTarget target : targets) {

            AnalyzerDedupResponseDto result =
                    resultMap.get(target.getArticleId());

            if (result == null) {
                log.warn(
                        "[ANALYSIS] missing result for articleId={}",
                        target.getArticleId()
                );
                continue;
            }

            ArticleMeta meta = articleMetaRepository
                    .findById(target.getMetaId())
                    .orElseThrow();

            if (result.isDuplicate()) {
                meta.markDuplicated();
                duplicated++;
            } else {
                meta.markAnalyzed();
                analyzed++;
            }
        }

        log.info(
                "[ANALYSIS] analyzed={}, duplicated={}",
                analyzed, duplicated
        );

        return analyzed;
    }

    @Transactional
    public void analyzeOneForTest() {

        // COLLECTED 상태 중 최신 1건 조회
        List<ArticleAnalysisTarget> targets =
                articleMetaRepository.findAnalysisTargets(
                        ArticleProcessStatus.COLLECTED,
                        LocalDateTime.now().minusHours(24),
                        LocalDateTime.now()
                );

        if (targets.isEmpty()) {
            log.warn("[ANALYSIS-TEST] no collected articles to test");
            return;
        }

        ArticleAnalysisTarget target = targets.get(0);

        // Analyzer 요청 DTO
        AnalyzerDedupRequestDto request =
                AnalyzerDedupRequestDto.from(target);

        // Analyzer 호출 (배치지만 1건 테스트)
        List<AnalyzerDedupResponseDto> results =
                analyzerClient.dedupBatch(List.of(request));

        if (results.isEmpty()) {
            log.warn(
                    "[ANALYSIS-TEST] analyzer returned empty result for articleId={}",
                    target.getArticleId()
            );
            return;
        }

        AnalyzerDedupResponseDto result = results.get(0);

        // Meta 상태 반영
        articleMetaRepository.findById(target.getMetaId())
                .ifPresent(meta -> {
                    if (result.isDuplicate()) {
                        meta.markDuplicated();
                    } else {
                        meta.markAnalyzed();
                    }
                });

        // 로그
        log.info(
                "[ANALYSIS-TEST] metaId={}, articleId={}, duplicate={}, score={}",
                target.getMetaId(),
                result.getArticleId(),
                result.isDuplicate(),
                result.getScore()
        );
    }


}
