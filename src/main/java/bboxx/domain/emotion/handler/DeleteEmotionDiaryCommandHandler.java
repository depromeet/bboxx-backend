package bboxx.domain.emotion.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.emotion.command.DeleteEmotionDiaryCommand;
import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;

public class DeleteEmotionDiaryCommandHandler implements CommandHandler<DeleteEmotionDiaryCommand, Void> {

    private final EmotionDiaryRepository emotionDiaryRepository;

    public DeleteEmotionDiaryCommandHandler(EmotionDiaryRepository emotionDiaryRepository) {
        this.emotionDiaryRepository = emotionDiaryRepository;
    }

    @Override
    public Void handle(DeleteEmotionDiaryCommand command) {
        emotionDiaryRepository.deleteById(command.getEmotionDiaryId());
        return null;
    }
}
