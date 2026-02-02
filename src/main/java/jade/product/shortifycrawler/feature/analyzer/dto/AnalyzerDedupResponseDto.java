package jade.product.shortifycrawler.feature.analyzer.dto;

import lombok.Getter;

@Getter
public class AnalyzerDedupResponseDto {
    private Long articleId;
    private boolean isDuplicate;
    private double score;
}
