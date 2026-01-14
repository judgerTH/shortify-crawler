package jade.product.shortifycrawler.domain.article.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ArticleFailLog {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String step;
    private String reason;

    @Lob
    private String detail;

    private LocalDateTime createdAt = LocalDateTime.now();
}
