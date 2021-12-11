package bboxx.application.controller.dto.response;

import bboxx.domain.emotion.Emotion;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetEmotionsResponse {
    @ApiModelProperty(value = "모든 감정 목록")
    private List<Emotion> emotions;
}
