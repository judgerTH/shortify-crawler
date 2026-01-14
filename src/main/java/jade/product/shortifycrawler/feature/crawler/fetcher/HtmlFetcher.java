package jade.product.shortifycrawler.feature.crawler.fetcher;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlFetcher {

    public String fetch(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .referrer("https://www.google.com")
                    .timeout(20000)
                    .get()
                    .html();
        } catch (Exception e) {
            throw new IllegalStateException("HTML_FETCH_FAILED: " + url, e);
        }
    }
}
