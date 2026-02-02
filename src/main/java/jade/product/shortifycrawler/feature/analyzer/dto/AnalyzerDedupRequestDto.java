package jade.product.shortifycrawler.feature.analyzer.dto;

import jade.product.shortifycrawler.domain.article.entity.OriginalArticle;
import jade.product.shortifycrawler.feature.analysis.dto.ArticleAnalysisTarget;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalyzerDedupRequestDto {

    private Long articleId;
    private String title;
    private String content;

    public static AnalyzerDedupRequestDto from(ArticleAnalysisTarget target) {
        return AnalyzerDedupRequestDto.builder()
                .articleId(target.getArticleId())
                .title(target.getTitle())
                .content(target.getContent())
                .build();
    }
}
