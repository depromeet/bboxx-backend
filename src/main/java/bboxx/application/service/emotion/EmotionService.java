package bboxx.application.service.emotion;

import bboxx.domain.emotion.EmotionStatus;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommandInfo;
import bboxx.infrastructure.repository.JpaEmotionRepository;
import bboxx.infrastructure.repository.JpaEmotionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final JpaEmotionRepository emotionRepository;

    @Transactional
    public void create(CreateEmotionDiaryCommand command) {
        emotionRepository.save(command.toEntity());
    }
}
