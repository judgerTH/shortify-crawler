package jade.product.shortifycrawler.domain.newsInsight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "news_insight"
)
public class NewsInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tension;
    private int positivity;
    private int stability;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public static NewsInsight create(
            int tension,
            int positivity,
            int stability,
            String summary
    ) {
        return NewsInsight.builder()
                .tension(tension)
                .positivity(positivity)
                .stability(stability)
                .summary(summary)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
