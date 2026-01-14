package jade.product.shortifycrawler.feature.schedule;

import jade.product.shortifycrawler.feature.crawler.batch.CrawlBatchService;
import jade.product.shortifycrawler.feature.crawler.batch.CrawlResult;
import jade.product.shortifycrawler.feature.summary.batch.SummaryBatchService;
import jade.product.shortifycrawler.domain.newsInsight.service.InsightPipelineService;
import jade.product.shortifycrawler.global.notify.DiscordNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsBatchScheduler {

    private final CrawlBatchService crawlBatchService;
    private final SummaryBatchService summaryBatchService;
    private final InsightPipelineService insightPipelineService;
    private final CrawlDailyStat crawlDailyStat;
    private final DiscordNotifier discordNotifier;

    /**
     * 뉴스 크롤링
     * 매 2시간 정각 (00, 02, 04, ...)
     */
    @Scheduled(cron = "0 0 */2 * * *")
    public void crawlNews() {
        log.info("[SCHEDULE] crawl start");

        CrawlResult result = crawlBatchService.run();

        crawlDailyStat.add(
                result.success(),
                result.fail()
        );

        log.info("[SCHEDULE] crawl end");
    }

    /**
     * 뉴스 요약
     * 매 2시간 5분 (00:05, 02:05, ...)
     */
    @Scheduled(cron = "0 5 */2 * * *")
    public void summarizeNews() {
        log.info("[SCHEDULE] summary start");
        summaryBatchService.run();
        log.info("[SCHEDULE] summary end");
    }

    /**
     * 뉴스 인사이트
     * 매일 06:00
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void generateInsight() {
        log.info("[SCHEDULE] insight start");
        try {
            insightPipelineService.generateTodayInsight();
        } catch (Exception e) {
            log.error("[SCHEDULE] insight failed", e);
        }
        log.info("[SCHEDULE] insight end");
    }

    /**
     * 디스코드 알람
     * 매일 21:00
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void sendDailyCrawlReport() {
        log.info("[SCHEDULE] daily crawl report start");

        String report = crawlDailyStat.flush();

        discordNotifier.send(report);

        log.info("[SCHEDULE] daily crawl report end");
    }

}
