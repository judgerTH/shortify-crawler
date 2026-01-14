package jade.product.shortifycrawler.domain.article.entity;

public enum ArticleProcessStatus {
    COLLECTED,      // 크롤링만 완료
    SUMMARY_DONE,   // 요약 완료
    SUMMARY_FAILED  // 요약 실패
}
