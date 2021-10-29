package bboxx.application.service.emotion;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.command.KeepImprovementDiaryCommand;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaEmotionDiaryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class KeepImprovementDiaryCommandHandler {

    private final JpaEmotionDiaryRepository emotionDiaryRepository;

    public KeepImprovementDiaryCommandHandler(JpaEmotionDiaryRepository emotionDiaryRepository) {
        this.emotionDiaryRepository = emotionDiaryRepository;
    }

    @Transactional
    public void handle(KeepImprovementDiaryCommand command) {
        EmotionDiary emotionDiary = emotionDiaryRepository.findById(command.getEmotionDiaryId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));
        emotionDiary.keepImprovementDiary(command.getTitle(), command.getContent(), command.getMemberId(), command.getTags());
        emotionDiaryRepository.save(emotionDiary);
    }
}
