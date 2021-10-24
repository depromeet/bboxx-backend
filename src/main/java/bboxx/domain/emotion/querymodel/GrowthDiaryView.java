package bboxx.domain.emotion.querymodel;

import bboxx.domain.emotion.GrowthDiaryTag;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class GrowthDiaryView {
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private Long emotionDiaryId;
    private List<String> tags;
    private LocalDateTime keptAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
