package jade.product.shortifycrawler.feature.crawler.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParserResolver {

    private final List<ArticleParser> parsers;

    public ArticleParser resolve(String url) {
        return parsers.stream()
                .filter(p -> p.supports(url))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("NO_PARSER_FOR_URL: " + url)
                );
    }
}
