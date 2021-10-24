package bboxx.application.service.emotion;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.command.KeepGrowthDiaryCommand;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaEmotionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class KeepGrowthDiaryCommandHandler {

    private final JpaEmotionRepository emotionRepository;

    public KeepGrowthDiaryCommandHandler(JpaEmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    @Transactional
    public void handle(KeepGrowthDiaryCommand command) {
        EmotionDiary emotionDiary = emotionRepository.findById(command.getEmotionDiaryId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));
        emotionDiary.keepGrowthDiary(command.getTitle(), command.getContent(), command.getMemberId(), command.getTags());
        emotionRepository.save(emotionDiary);
    }
}
