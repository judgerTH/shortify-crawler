package jade.product.shortifycrawler.feature.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleAnalysisTarget {

    private Long metaId;      // ArticleMeta id
    private Long articleId;   // OriginalArticle id
    private String title;
    private String content;
}
