package jade.product.shortifycrawler.domain.article.service;

import jade.product.shortifycrawler.domain.article.entity.ArticleMeta;
import jade.product.shortifycrawler.domain.article.entity.OriginalArticle;
import jade.product.shortifycrawler.domain.article.repository.ArticleMetaRepository;
import jade.product.shortifycrawler.domain.article.repository.OriginalArticleRepository;
import jade.product.shortifycrawler.feature.crawler.dto.ArticleContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticlePersistService {

    private final OriginalArticleRepository originalRepo;
    private final ArticleMetaRepository metaRepo;

    @Transactional
    public void save(ArticleContent content) {

        // URL 기준 중복 방지
        if (metaRepo.existsByUrl(content.getUrl())) {
            return;
        }

        // 1. 원문 저장
        OriginalArticle original = OriginalArticle.create(
                content.getUrl(),
                content.getTitle(),
                content.getContent(),
                content.getPress(),
                content.getPublishedAt()
        );
        originalRepo.save(original);

        // 2. 메타 저장
        ArticleMeta meta = ArticleMeta.fromOriginal(original);
        metaRepo.save(meta);
    }
}
