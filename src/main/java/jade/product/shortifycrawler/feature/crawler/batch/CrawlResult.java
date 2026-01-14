package jade.product.shortifycrawler.feature.crawler.batch;

public record CrawlResult(
        int success,
        int fail,
        long duration
) {}
