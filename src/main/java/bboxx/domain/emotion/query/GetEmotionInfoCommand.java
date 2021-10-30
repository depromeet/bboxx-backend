package bboxx.domain.emotion.query;

import bboxx.domain.emotion.Emotion;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetEmotionInfoCommand {
    @ApiModelProperty(value = "모든 감정 목록")
    private List<Emotion> emotions;

}
