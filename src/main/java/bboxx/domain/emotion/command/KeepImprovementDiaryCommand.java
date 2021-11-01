package bboxx.domain.emotion.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KeepImprovementDiaryCommand {
    @ApiModelProperty(value = "감정 일기 고유 id", example = "1")
    private Long emotionDiaryId;
    @ApiModelProperty(value = "성장 일기 제목", example = "Sad Day")
    private String title;
    @ApiModelProperty(value = "성장 일기 내용", example = "I'm so Sad. Because I dropped my candy to the ground.")
    private String content;
    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long memberId;
    @ApiModelProperty(value = "성장 일기 작성 후 태그한 감정 카테고리 목록", example = "['sad']")
    private List<String> tags;
}
