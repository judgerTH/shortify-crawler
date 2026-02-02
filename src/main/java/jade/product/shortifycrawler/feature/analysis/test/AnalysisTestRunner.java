//package jade.product.shortifycrawler.feature.analysis.test;
//
//import jade.product.shortifycrawler.feature.analysis.service.ArticleAnalysisService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class AnalysisTestRunner implements ApplicationRunner {
//
//    private final ArticleAnalysisService analysisService;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        log.info("[ANALYSIS-TEST] start");
//        try {
//            analysisService.analyzeOneForTest();
//        } catch (Exception e) {
//            log.error("[ANALYSIS-TEST] failed", e);
//        }
//        log.info("[ANALYSIS-TEST] end");
//    }
//}
