package jade.product.shortifycrawler.domain.article.entity;

public enum ArticleProcessStatus {

    COLLECTED,      // 크롤링만 완료

    ANALYZED,       // 분석 완료 (중복 아님)
    DUPLICATED,     // 분석 결과 중복 기사

    SUMMARY_DONE,   // 요약 완료
    SUMMARY_FAILED  // 요약 실패
}
