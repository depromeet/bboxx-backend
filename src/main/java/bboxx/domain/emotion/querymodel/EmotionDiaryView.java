package bboxx.domain.emotion.querymodel;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.EmotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class EmotionDiaryView {
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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
