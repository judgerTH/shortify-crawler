package jade.product.shortifycrawler.feature.summary.service;

import jade.product.shortifycrawler.global.llm.GeminiClient;
import jade.product.shortifycrawler.global.llm.NewsPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final GeminiClient geminiClient;
    private final NewsPromptBuilder promptBuilder;

    public String summarize(String content) {

        String prompt = promptBuilder.build(content);
        return geminiClient.generate(prompt);
    }
}
