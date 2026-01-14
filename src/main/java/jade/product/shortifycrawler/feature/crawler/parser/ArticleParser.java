package jade.product.shortifycrawler.feature.crawler.parser;

import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;

public interface ArticleParser {

    boolean supports(String url);

    ArticleContent parse(String url, String html);
}
