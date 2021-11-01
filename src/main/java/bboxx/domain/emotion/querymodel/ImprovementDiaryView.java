package bboxx.domain.emotion.querymodel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class ImprovementDiaryView {
    @ApiModelProperty(value = "성장 일기 고유 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "성장 일기 제목", example = "Sad Day")
    private String title;
    @ApiModelProperty(value = "성장 일기 내용", example = "I'm so Sad. Because I dropped my candy to the ground.")
    private String content;
    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long memberId;
    @ApiModelProperty(value = "감정 일기 고유 id", example = "1")
    private Long emotionDiaryId;
    @ApiModelProperty(value = "성장 일기 작성 후 태그한 감정 카테고리 목록", example = "['sad']")
    private List<String> tags;
    @ApiModelProperty(value = "감정 일기 작성 시간", example = "2021-11-01T23:24:52.887247")
    private LocalDateTime keptAt;
    @ApiModelProperty(value = "감정 일기 작성 시간", example = "2021-11-01T23:24:52.887247")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "감정 일기 업데이트 시간", example = "2021-11-01T23:24:52.887247")
    private LocalDateTime updatedAt;
}
