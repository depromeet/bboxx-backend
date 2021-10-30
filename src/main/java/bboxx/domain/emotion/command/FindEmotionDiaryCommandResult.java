package bboxx.domain.emotion.command;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FindEmotionDiaryCommandResult {

    private EmotionDiary emotionDiary;
//    private List<Emotion> emotions;

}
