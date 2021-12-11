package bboxx.application.config;

import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import bboxx.domain.emotion.commandmodel.EmotionRepository;
import bboxx.domain.emotion.commandmodel.ImprovementDiaryRepository;
import bboxx.domain.emotion.handler.CreateEmotionDiaryCommandHandler;
import bboxx.domain.emotion.handler.DeleteEmotionDiaryCommandHandler;
import bboxx.domain.emotion.handler.KeepImprovementDiaryCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EmotionConfiguration {

    private final EmotionRepository emotionRepository;

    private final EmotionDiaryRepository emotionDiaryRepository;

    private final ImprovementDiaryRepository improvementDiaryRepository;

    @Bean
    public KeepImprovementDiaryCommandHandler keepImprovementDiaryCommandHandler() {
        return new KeepImprovementDiaryCommandHandler(emotionDiaryRepository, improvementDiaryRepository);
    }

    @Bean
    public CreateEmotionDiaryCommandHandler createEmotionDiaryCommandHandler() {
        return new CreateEmotionDiaryCommandHandler(emotionRepository, emotionDiaryRepository);
    }

    @Bean
    public DeleteEmotionDiaryCommandHandler deleteEmotionDiaryCommandHandler() {
        return new DeleteEmotionDiaryCommandHandler(emotionDiaryRepository);
    }
}
