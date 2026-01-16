package jade.product.shortifycrawler.domain.article.repository;

import jade.product.shortifycrawler.domain.article.entity.ArticleMeta;
import jade.product.shortifycrawler.domain.article.entity.ArticleProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleMetaRepository
        extends JpaRepository<ArticleMeta, Long> {

    boolean existsByUrl(String url);

    List<ArticleMeta> findTop20ByStatusOrderByCollectedAtDesc(
            ArticleProcessStatus status
    );

}
