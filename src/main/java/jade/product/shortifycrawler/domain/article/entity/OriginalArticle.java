package jade.product.shortifycrawler.domain.article.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginalArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255)
    private String title;

    @Column(length = 500)
    private String url;

    @Column(length = 255)
    private String press;

    private LocalDateTime publishedAt;
    private LocalDateTime crawledAt;
    private LocalDateTime createdAt;

    // 도메인 기준 팩토리
    public static OriginalArticle create(
            String url,
            String title,
            String content,
            String press,
            LocalDateTime publishedAt
    ) {
        return OriginalArticle.builder()
                .url(url)
                .title(title)
                .content(content)
                .press(press)
                .publishedAt(publishedAt)
                .crawledAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
