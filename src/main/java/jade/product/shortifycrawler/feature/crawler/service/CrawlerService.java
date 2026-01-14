package jade.product.shortifycrawler.feature.crawler.service;

import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;
import jade.product.shortifycrawler.feature.crawler.fetcher.HtmlFetcher;
import jade.product.shortifycrawler.feature.crawler.parser.ArticleParser;
import jade.product.shortifycrawler.feature.crawler.parser.ParserResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerService {

    private final HtmlFetcher htmlFetcher;
    private final ParserResolver parserResolver;

    public ArticleContent crawl(String url) {

        String html = htmlFetcher.fetch(url);

        ArticleParser parser = parserResolver.resolve(url);

        ArticleContent content = parser.parse(url, html);

        log.info(
                "[CRAWL] success title='{}', press='{}', len={}",
                content.getTitle(),
                content.getPress(),
                content.getContent().length()
        );

        return content;
    }

}
