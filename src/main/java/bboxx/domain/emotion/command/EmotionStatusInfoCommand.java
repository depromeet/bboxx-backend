package bboxx.domain.emotion.command;

import bboxx.domain.emotion.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EmotionStatusInfoCommand {

    private List<Emotion> emotions;

}
