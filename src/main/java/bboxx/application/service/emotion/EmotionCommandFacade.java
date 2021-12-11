package bboxx.application.service.emotion;

import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.DeleteEmotionDiaryCommand;
import bboxx.domain.emotion.command.KeepImprovementDiaryCommand;
import bboxx.domain.emotion.handler.CreateEmotionDiaryCommandHandler;
import bboxx.domain.emotion.handler.DeleteEmotionDiaryCommandHandler;
import bboxx.domain.emotion.handler.KeepImprovementDiaryCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EmotionCommandFacade {

    private final CreateEmotionDiaryCommandHandler createEmotionDiaryCommandHandler;
    private final DeleteEmotionDiaryCommandHandler deleteEmotionDiaryCommandHandler;

    private final KeepImprovementDiaryCommandHandler keepImprovementDiaryCommandHandler;

    @Transactional
    public void createEmotionDiary(CreateEmotionDiaryCommand command) {
        createEmotionDiaryCommandHandler.handle(command);
    }

    @Transactional
    public void deleteEmotionDiary(Long id) {
        deleteEmotionDiaryCommandHandler.handle(new DeleteEmotionDiaryCommand(id));
    }

    @Transactional
    public void keep(KeepImprovementDiaryCommand command) {
        keepImprovementDiaryCommandHandler.handle(command);
    }
}
