package bboxx.domain.emotion.command;

import bboxx.domain.emotion.EmotionDiary;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateEmotionDiaryCommand {

    private String title;

    private String content;

    private Long memberId;

    private Long categoryId;

    private String emotionStatuses;

    public EmotionDiary toEntity() {
        return EmotionDiary.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .categoryId(categoryId)
                .emotionStatuses(emotionStatuses)
                .isNotiSent(false)
                .build();
    }

}
