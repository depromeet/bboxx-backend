package bboxx.domain.emotion.querymodel;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.EmotionStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class EmotionDiaryView {
    @ApiModelProperty(value = "감정 일기 고유 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "감정 일기 제목", example = "Sad Day")
    private String title;
    @ApiModelProperty(value = "감정 일기 내용", example = "I'm so Sad. Because I dropped my candy to the ground.")
    private String content;
    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long memberId;
    @ApiModelProperty(value = "카테고리 고유 id", example = "1")
    private Long categoryId;
    @ApiModelProperty(value = "감정 일기 생성 시간", example = "2021-10-30T23:24:52.887247")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "감정 일기 업데이트 시간", example = "2021-10-30T23:24:52.887247")
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "감정 일기 작성 당시의 사용자 감정 상태 목록")
    private List<EmotionStatusView> emotionStatusList;

    public EmotionDiaryView(EmotionDiary emotionDiary) {
        this.id = emotionDiary.getId();
        this.title = emotionDiary.getTitle();
        this.content = emotionDiary.getContent();
        this.memberId = emotionDiary.getMemberId();
        this.categoryId = emotionDiary.getCategoryId();
        this.createdAt = emotionDiary.getCreatedAt();
        this.updatedAt = emotionDiary.getUpdatedAt();
        this.emotionStatusList = new ArrayList<>();

        for (EmotionStatus emotionStatus : emotionDiary.getEmotionStatusList()) {
            EmotionStatusView emotionStatusView = new EmotionStatusView(emotionStatus.getEmotion().getId(), emotionStatus.getEmotion().getStatus(), emotionStatus.getEmotion().getEmotionUrl());
            emotionStatusList.add(emotionStatusView);
        }
    }
}
