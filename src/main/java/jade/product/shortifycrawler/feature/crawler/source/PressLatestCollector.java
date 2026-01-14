package jade.product.shortifycrawler.feature.crawler.source;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PressLatestCollector {

    private static final String NAVER_PRESS_BASE = "https://media.naver.com/press/";

    public Map<String, String> collectLatestByPress() {

        Map<String, String> result = new HashMap<>();

        for (String pressId : getAllPressIds()) {
            try {
                String url = NAVER_PRESS_BASE + pressId;

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();

                doc.select("a[href*='n.news.naver.com/article']")
                        .stream()
                        .findFirst()
                        .map(e -> e.attr("href"))
                        .ifPresent(articleUrl ->
                                result.put(pressId, articleUrl)
                        );

            } catch (Exception ignore) {}
        }

        return result;
    }

    // 네이버에 등록된 주요 언론사 코드 전체 목록
    public List<String> getAllPressIds() {
        return List.of(
                "056", // KBS
                "214", // MBC
                "001", // YNA
                "005", // 국민일보
                "011", // 서울경제
                "015", // 한국경제
                "020", // 동아일보
                "021", // 문화일보
                "022", // 세계일보
                "023", // 조선일보
                "025", // 중앙일보
                "448", // SBS
                "079", // 노컷뉴스
                "138", // 디지털데일리
                "277", // 아시아경제
                "009", // 매일경제
                "469" // 한국일보
                // 필요하면 더 추가 가능
        );
    }
}
