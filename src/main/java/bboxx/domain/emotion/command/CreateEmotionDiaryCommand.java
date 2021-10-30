package bboxx.domain.emotion.command;

import bboxx.domain.emotion.EmotionDiary;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateEmotionDiaryCommand {
    @ApiModelProperty(value = "감정 일기 제목", example = "Sad Day")
    private String title;
    @ApiModelProperty(value = "감정 일기 내용", example = "I'm so Sad. Because I dropped my candy to the ground.")
    private String content;
    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long memberId;
    @ApiModelProperty(value = "카테고리 고유 id", example = "1")
    private Long categoryId;
    @ApiModelProperty(value = "사용자의 기분에 대한 id 목록", example = "[1, 3]")
    private List<Long> emotionStatusList;

}
