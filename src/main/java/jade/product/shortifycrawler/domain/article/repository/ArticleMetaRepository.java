package jade.product.shortifycrawler.domain.article.repository;

import jade.product.shortifycrawler.domain.article.entity.ArticleMeta;
import jade.product.shortifycrawler.domain.article.entity.ArticleProcessStatus;
import jade.product.shortifycrawler.feature.analysis.dto.ArticleAnalysisTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleMetaRepository
        extends JpaRepository<ArticleMeta, Long> {

    boolean existsByUrl(String url);

    List<ArticleMeta> findTop20ByStatusOrderByCollectedAtDesc(
            ArticleProcessStatus status
    );

    List<ArticleMeta> findByStatusAndCollectedAtBetween(
            ArticleProcessStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    @Query("""
        select new jade.product.shortifycrawler.feature.analysis.dto.ArticleAnalysisTarget(
            m.id,
            o.id,
            o.title,
            o.content
        )
        from ArticleMeta m
        join OriginalArticle o on m.url = o.url
        where m.status = :status
          and m.collectedAt between :from and :to
    """)
    List<ArticleAnalysisTarget> findAnalysisTargets(
            ArticleProcessStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    Optional<ArticleMeta> findFirstByStatusOrderByCollectedAtAsc(ArticleProcessStatus status);

}
