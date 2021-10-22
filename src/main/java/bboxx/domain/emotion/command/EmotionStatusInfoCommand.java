package bboxx.domain.emotion.command;

import bboxx.domain.emotion.EmotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EmotionStatusInfoCommand {

    private List<EmotionStatus> emotionStatuses;

}