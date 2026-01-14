package jade.product.shortifycrawler.feature.schedule;

import org.springframework.stereotype.Component;

@Component
public class CrawlDailyStat {

    private int totalSuccess = 0;
    private int totalFail = 0;

    public synchronized void add(int success, int fail) {
        totalSuccess += success;
        totalFail += fail;
    }

    public synchronized String flush() {
        String message = """
🟢 Shortify 일일 뉴스 크롤링 리포트 (21:00)

- 총 수집 기사: %d
- 총 실패: %d
        """.formatted(totalSuccess, totalFail);

        // 다음 날을 위해 초기화
        totalSuccess = 0;
        totalFail = 0;

        return message;
    }
}
