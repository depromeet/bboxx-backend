package bboxx.application.service.emotion;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import bboxx.domain.emotion.commandmodel.EmotionRepository;
import bboxx.domain.emotion.querymodel.EmotionDiaryView;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmotionQueryFacade {

    private final EmotionDiaryRepository emotionDiaryRepository;
    private final EmotionRepository emotionRepository;

    public List<Emotion> getEmotionInfo() {
        return emotionRepository.findAll();
    }

    public EmotionDiaryView findEmotionDiary(Long id) {
        EmotionDiary emotionDiary = emotionDiaryRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));

        return new EmotionDiaryView(emotionDiary);
    }
}
