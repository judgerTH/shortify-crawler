package jade.product.shortifycrawler.feature.insight.dto;

public record NewsInsightResponse(
        int tension,
        int positivity,
        int stability,
        String summary
) {
}
