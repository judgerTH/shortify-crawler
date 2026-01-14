package jade.product.shortifycrawler.feature.crawler.service;

import jade.product.shortifycrawler.domain.article.service.ArticlePersistService;
import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineService {

    private final CrawlerService crawlerService;
    private final ArticlePersistService persistService;

    public void process(String url) {

        // 1. 크롤링 (HTML → ArticleContent)
        ArticleContent content = crawlerService.crawl(url);

        // 2. DB 저장
        persistService.save(content);
    }
}
