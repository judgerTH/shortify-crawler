package jade.product.shortifycrawler.domain.article.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "article_meta",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "url")
        }
)
public class ArticleMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String press;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    private LocalDateTime publishedAt;
    private LocalDateTime collectedAt;

    private String category;
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleProcessStatus status;

    @PrePersist
    void prePersist() {
        if (this.status == null) {
            this.status = ArticleProcessStatus.COLLECTED;
        }
        if (this.collectedAt == null) {
            this.collectedAt = LocalDateTime.now();
        }
    }

    public static ArticleMeta fromOriginal(OriginalArticle original) {
        return ArticleMeta.builder()
                .press(original.getPress())
                .title(original.getTitle())
                .url(original.getUrl())
                .publishedAt(original.getPublishedAt())
                .collectedAt(LocalDateTime.now())
                .status(ArticleProcessStatus.COLLECTED)
                .build();
    }

    public void markSummaryDone() {
        this.status = ArticleProcessStatus.SUMMARY_DONE;
    }

    public void markSummaryFailed() {
        this.status = ArticleProcessStatus.SUMMARY_FAILED;
    }
}
