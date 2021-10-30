package bboxx.application.service.emotion;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.EmotionStatusInfoCommand;
import bboxx.domain.emotion.command.FindEmotionDiaryCommandResult;
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


    public EmotionStatusInfoCommand emotionStatusInfo() {
        return new EmotionStatusInfoCommand(emotionRepository.findAll());
    }

    @Transactional
    public void createEmotionDiary(CreateEmotionDiaryCommand command) {
        emotionDiaryRepository.save(command.toEntity());
    }

    public FindEmotionDiaryCommandResult findEmotionDiary(Long id) {
        // 감정 일기
        EmotionDiary emotionDiary = emotionDiaryRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));

        // 감정 상태
        String[] emotionStatusArray = emotionDiary.getEmotionStatuses().replace(" ", "").split(",");
        List<Emotion> emotions = new ArrayList<>();
        for (String status: emotionStatusArray) {
            Long statusNum = Long.valueOf(status);
            Emotion emotion = emotionRepository.findById(statusNum)
                    .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_STATUS_NOT_FOUND_ERROR));
            emotions.add(emotion);
        }
        return new FindEmotionDiaryCommandResult(emotionDiary, emotions);
    }

    @Transactional
    public void deleteEmotionDiary(Long id) {
        emotionDiaryRepository.deleteById(id);
    }
}
