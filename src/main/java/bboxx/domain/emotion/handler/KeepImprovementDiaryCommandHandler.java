package bboxx.domain.emotion.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.emotion.ImprovementDiary;
import bboxx.domain.emotion.command.KeepImprovementDiaryCommand;
import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import bboxx.domain.emotion.commandmodel.ImprovementDiaryRepository;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;

public class KeepImprovementDiaryCommandHandler implements CommandHandler<KeepImprovementDiaryCommand, Long> {

    private final EmotionDiaryRepository emotionDiaryRepository;

    private final ImprovementDiaryRepository improvementDiaryRepository;

    public KeepImprovementDiaryCommandHandler(EmotionDiaryRepository emotionDiaryRepository, ImprovementDiaryRepository improvementDiaryRepository) {
        this.emotionDiaryRepository = emotionDiaryRepository;
        this.improvementDiaryRepository = improvementDiaryRepository;
    }

    public Long handle(KeepImprovementDiaryCommand command) {
        emotionDiaryRepository.findById(command.getEmotionDiaryId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));

        ImprovementDiary improvementDiary = new ImprovementDiary(
                command.getTitle(),
                command.getContent(),
                command.getMemberId(),
                command.getEmotionDiaryId(),
                command.getTags()
        );

        improvementDiaryRepository.save(improvementDiary);

        return improvementDiary.getId();
    }
}
