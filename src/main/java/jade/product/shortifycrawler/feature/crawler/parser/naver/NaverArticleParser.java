package jade.product.shortifycrawler.feature.crawler.parser.naver;

import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;
import jade.product.shortifycrawler.feature.crawler.parser.ArticleParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NaverArticleParser implements ArticleParser {

    @Override
    public boolean supports(String url) {
        return url.contains("news.naver.com");
    }

    @Override
    public ArticleContent parse(String url, String html) {

        Document doc = Jsoup.parse(html);

        String title = doc
                .selectFirst("h2#title_area span")
                .text();

        String content = doc
                .select("article#dic_area")
                .text();

        String press = doc
                .selectFirst("a.media_end_head_top_logo img")
                .attr("alt");

        return ArticleContent.builder()
                .url(url)
                .title(title)
                .content(content)
                .press(press)
                .publishedAt(LocalDateTime.now())
                .build();
    }
}
