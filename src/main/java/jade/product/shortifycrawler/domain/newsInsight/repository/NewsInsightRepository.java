package jade.product.shortifycrawler.domain.newsInsight.repository;

import jade.product.shortifycrawler.domain.newsInsight.entity.NewsInsight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsInsightRepository extends JpaRepository<NewsInsight, Long> {
}
