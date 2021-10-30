package bboxx.domain.emotion.command;

import bboxx.domain.emotion.EmotionDiary;
import lombok.*;

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

    private List<Long> emotionStatusList;

}
