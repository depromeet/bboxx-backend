package bboxx.domain.emotion.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteEmotionDiaryCommand {
    @ApiModelProperty(value = "감정 일기 고유 id", example = "1")
    private Long emotionDiaryId;
}
