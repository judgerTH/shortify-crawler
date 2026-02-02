package jade.product.shortifycrawler.feature.crawler.batch;

import jade.product.shortifycrawler.feature.crawler.service.PipelineService;
import jade.product.shortifycrawler.feature.crawler.source.PressLatestCollector;
import jade.product.shortifycrawler.global.notify.DiscordNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlBatchService {

    private final PressLatestCollector pressLatestCollector;
    private final PipelineService pipelineService;
    private final DiscordNotifier discordNotifier;

    // 이후 파이썬 머신러닝 모델 합류 예정
    public CrawlResult run() {

        long start = System.currentTimeMillis();
        int success = 0;
        int fail = 0;

        log.info("[CRAWL-BATCH] start");

        try {
            Map<String, String> pressUrlMap =
                    pressLatestCollector.collectLatestByPress();

            for (String url : pressUrlMap.values()) {
                try {
                    pipelineService.process(url);
                    success++;
                    Thread.sleep(2000);
                } catch (Exception e) {
                    fail++;
                    log.error("[CRAWL-BATCH] fail url={}", url, e);
                }
            }

        } catch (Exception e) {
            // 🔴 치명적 실패는 즉시 알림
            discordNotifier.send("""
🔴 Shortify 뉴스 크롤링 전체 실패

에러: %s
            """.formatted(e.getMessage()));
            throw e;
        }

        long duration = (System.currentTimeMillis() - start) / 1000;

        log.info("[CRAWL-BATCH] end");

        return new CrawlResult(success, fail, duration);
    }
}
