package jade.product.shortifycrawler.domain.article.repository;

import jade.product.shortifycrawler.domain.article.entity.OriginalArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OriginalArticleRepository
        extends JpaRepository<OriginalArticle, Long> {
    Optional<OriginalArticle> findByUrl(String url);
}
