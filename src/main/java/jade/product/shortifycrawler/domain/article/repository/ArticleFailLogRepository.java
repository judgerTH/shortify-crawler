package jade.product.shortifycrawler.domain.article.repository;

import jade.product.shortifycrawler.domain.article.entity.ArticleFailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleFailLogRepository
        extends JpaRepository<ArticleFailLog, Long> {
}
