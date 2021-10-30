package bboxx.application.service.emotion;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.query.GetEmotionInfoCommand;
import bboxx.domain.emotion.querymodel.EmotionDiaryView;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaEmotionDiaryRepository;
import bboxx.infrastructure.repository.JpaEmotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmotionDiaryService {

    private final JpaEmotionDiaryRepository emotionDiaryRepository;

    private final JpaEmotionRepository emotionRepository;


    public GetEmotionInfoCommand getEmotionInfo() {
        return new GetEmotionInfoCommand(emotionRepository.findAll());
    }

    @Transactional
    public void createEmotionDiary(CreateEmotionDiaryCommand command) {
        List<Emotion> emotionList = new ArrayList<>();
        for(Long emotionStatusId : command.getEmotionStatusList()) {
            log.info(String.valueOf(emotionStatusId));
            Emotion emotion = emotionRepository.findById(emotionStatusId)
                    .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_NOT_FOUND_ERROR));
            emotionList.add(emotion);
        }

        EmotionDiary emotionDiary = new EmotionDiary(command.getTitle(), command.getContent(), command.getMemberId(), command.getCategoryId(), emotionList);
        emotionDiaryRepository.save(emotionDiary);
    }

    public EmotionDiaryView findEmotionDiary(Long id) {
        EmotionDiary emotionDiary = emotionDiaryRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));

        return new EmotionDiaryView(emotionDiary);
    }

    @Transactional
    public void deleteEmotionDiary(Long id) {
        emotionDiaryRepository.deleteById(id);
    }
}
