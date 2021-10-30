package bboxx.domain.emotion.querymodel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EmotionStatusView {
    @ApiModelProperty(value = "감정 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "감정 상태 문구", example = "울고싶어")
    private String status;
    @ApiModelProperty(value = "감정 이미지 URL", example = "url")
    private String emotionUrl;

    public EmotionStatusView(Long id, String status, String emotionUrl) {
        this.id = id;
        this.status = status;
        this.emotionUrl = emotionUrl;
    }
}
