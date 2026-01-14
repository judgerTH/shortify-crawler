package jade.product.shortifycrawler.domain.article.repository;

import jade.product.shortifycrawler.domain.article.entity.ArticleSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleSummaryRepository
        extends JpaRepository<ArticleSummary, Long> {
    List<ArticleSummary> findTop20ByOrderByIdDesc();
}
