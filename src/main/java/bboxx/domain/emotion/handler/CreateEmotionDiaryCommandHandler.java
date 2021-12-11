package bboxx.domain.emotion.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import bboxx.domain.emotion.commandmodel.EmotionRepository;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateEmotionDiaryCommandHandler implements CommandHandler<CreateEmotionDiaryCommand, Long> {

    private final EmotionRepository emotionRepository;

    private final EmotionDiaryRepository emotionDiaryRepository;

    public CreateEmotionDiaryCommandHandler(EmotionRepository emotionRepository, EmotionDiaryRepository emotionDiaryRepository) {
        this.emotionRepository = emotionRepository;
        this.emotionDiaryRepository = emotionDiaryRepository;
    }

    @Override
    public Long handle(CreateEmotionDiaryCommand command) {
        List<Emotion> emotionList = new ArrayList<>();
        for(Long emotionStatusId : command.getEmotionStatusList()) {
            log.info(String.valueOf(emotionStatusId));
            Emotion emotion = emotionRepository.findById(emotionStatusId)
                    .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_NOT_FOUND_ERROR));
            emotionList.add(emotion);
        }

        EmotionDiary emotionDiary = new EmotionDiary(command.getTitle(), command.getContent(), command.getMemberId(), command.getCategoryId(), emotionList);
        emotionDiaryRepository.save(emotionDiary);

        return emotionDiary.getId();
    }
}
