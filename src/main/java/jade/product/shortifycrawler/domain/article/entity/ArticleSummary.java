package jade.product.shortifycrawler.domain.article.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String summaryTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summaryContent;

    private String keywords;

    private String modelName;

    @ManyToOne
    @JoinColumn(name = "article_meta_id", nullable = false)
    private ArticleMeta articleMeta;

    public static ArticleSummary create(
            String title,
            String content,
            String keywords,
            String modelName,
            ArticleMeta meta
    ) {
        return ArticleSummary.builder()
                .summaryTitle(title)
                .summaryContent(content)
                .keywords(keywords)
                .modelName(modelName)
                .createdAt(LocalDateTime.now())
                .articleMeta(meta)
                .build();
    }
}
