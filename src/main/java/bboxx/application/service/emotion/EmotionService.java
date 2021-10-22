package bboxx.application.service.emotion;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionStatus;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommandInfo;
import bboxx.domain.emotion.command.FindEmotionDiaryCommandResult;
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
public class EmotionService {

    private final JpaEmotionRepository emotionRepository;

    private final JpaEmotionStatusRepository emotionStatusRepository;


    @Transactional
    public CreateEmotionDiaryCommandInfo createInfo() {
        return new CreateEmotionDiaryCommandInfo(emotionStatusRepository.findAll());
    }

    @Transactional
    public void create(CreateEmotionDiaryCommand command) {
        emotionRepository.save(command.toEntity());
    }

    @Transactional
    public FindEmotionDiaryCommandResult findEmotionDiary(Long id) {
        // 감정 일기
        Emotion emotion = emotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("감정 일기를 찾지 못했습니다."));

        // 감정 상태
        String[] emotionStatusArray = emotion.getEmotionStatuses().replace(" ", "").split(",");
        List<EmotionStatus> emotionStatuses = new ArrayList<>();
        for (String status: emotionStatusArray) {
            Long statusNum = Long.valueOf(status);
            EmotionStatus emotionStatus = emotionStatusRepository.findById(statusNum)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 감정상태 입니다."));
            emotionStatuses.add(emotionStatus);
        }
        return new FindEmotionDiaryCommandResult(emotion, emotionStatuses);
    }

    @Transactional
    public void delete(Long id) {
        emotionRepository.deleteById(id);
    }
}
