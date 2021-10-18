package bboxx.domain.emotion.command;

import bboxx.domain.emotion.Emotion;
import lombok.*;

import javax.persistence.Column;
import java.util.List;

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

    public Emotion toEntity() {
        return Emotion.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .categoryId(categoryId)
                .emotionStatuses(emotionStatuses)
                .build();
    }

}
