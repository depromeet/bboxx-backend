package bboxx.domain.emotion.query;

import bboxx.domain.emotion.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetEmotionInfoCommand {

    private List<Emotion> emotions;

}
