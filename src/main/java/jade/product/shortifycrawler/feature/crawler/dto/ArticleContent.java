package jade.product.shortifycrawler.feature.crawler.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleContent {

    private final String url;
    private final String title;
    private final String content;
    private final String press;
    private final LocalDateTime publishedAt;
}
