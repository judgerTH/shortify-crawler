package jade.product.shortifycrawler.feature.crawler.parser;

import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewsArticleParser implements ArticleParser {

    @Override
    public boolean supports(String url) {
        // 현재는 네이버 뉴스만 대상
        return url.contains("news.naver.com");
    }

    @Override
    public ArticleContent parse(String url, String html) {

        Document doc = Jsoup.parse(html);

        String title = doc.selectFirst("h2#title_area span") != null
                ? doc.selectFirst("h2#title_area span").text()
                : "";

        String content = doc.select("article#dic_area").text();

        if (title.isBlank() || content.isBlank()) {
            throw new IllegalArgumentException("INVALID_ARTICLE");
        }

        String press = doc.selectFirst("a.media_end_head_top_logo img") != null
                ? doc.selectFirst("a.media_end_head_top_logo img").attr("alt")
                : "UNKNOWN";

        return ArticleContent.builder()
                .url(url)
                .title(title)
                .content(content)
                .press(press)
                .publishedAt(LocalDateTime.now())
                .build();
    }
}
