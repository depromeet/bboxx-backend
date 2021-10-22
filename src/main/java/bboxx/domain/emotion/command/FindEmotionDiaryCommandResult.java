package bboxx.domain.emotion.command;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FindEmotionDiaryCommandResult {

    private Emotion emotion;
    private List<EmotionStatus> emotionStatuses;

}
