package bboxx.application.service.emotion;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.EmotionStatus;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.EmotionStatusInfoCommand;
import bboxx.domain.emotion.command.FindEmotionDiaryCommandResult;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaEmotionRepository;
import bboxx.infrastructure.repository.JpaEmotionStatusRepository;
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

    private final JpaEmotionRepository emotionRepository;

    private final JpaEmotionStatusRepository emotionStatusRepository;


    public EmotionStatusInfoCommand emotionStatusInfo() {
        return new EmotionStatusInfoCommand(emotionStatusRepository.findAll());
    }

    @Transactional
    public void createEmotionDiary(CreateEmotionDiaryCommand command) {
        emotionRepository.save(command.toEntity());
    }

    public FindEmotionDiaryCommandResult findEmotionDiary(Long id) {
        // 감정 일기
        EmotionDiary emotionDiary = emotionRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));

        // 감정 상태
        String[] emotionStatusArray = emotionDiary.getEmotionStatuses().replace(" ", "").split(",");
        List<EmotionStatus> emotionStatuses = new ArrayList<>();
        for (String status: emotionStatusArray) {
            Long statusNum = Long.valueOf(status);
            EmotionStatus emotionStatus = emotionStatusRepository.findById(statusNum)
                    .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_STATUS_NOT_FOUND_ERROR));
            emotionStatuses.add(emotionStatus);
        }
        return new FindEmotionDiaryCommandResult(emotionDiary, emotionStatuses);
    }

    @Transactional
    public void deleteEmotionDiary(Long id) {
        emotionRepository.deleteById(id);
    }
}
