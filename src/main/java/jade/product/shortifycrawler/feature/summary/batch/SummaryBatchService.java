package jade.product.shortifycrawler.feature.summary.batch;

import jade.product.shortifycrawler.domain.article.entity.ArticleMeta;
import jade.product.shortifycrawler.domain.article.entity.ArticleProcessStatus;
import jade.product.shortifycrawler.domain.article.repository.ArticleMetaRepository;
import jade.product.shortifycrawler.domain.article.service.SummaryPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryBatchService {

    private final ArticleMetaRepository metaRepo;
    private final SummaryPipelineService pipelineService;

    public void run() {

        List<ArticleMeta> targets =
                metaRepo.findTop20ByStatusOrderByCollectedAtDesc(
                        ArticleProcessStatus.COLLECTED
                );

        log.info("[SUMMARY-BATCH] target={}", targets.size());

        for (ArticleMeta meta : targets) {
            try {
                pipelineService.process(meta);
                Thread.sleep(1500);
            } catch (Exception e) {
                log.error("[SUMMARY-BATCH] fail metaId={}", meta.getId(), e);
            }
        }
    }
}

