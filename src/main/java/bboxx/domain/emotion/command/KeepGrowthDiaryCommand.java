package bboxx.domain.emotion.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KeepGrowthDiaryCommand {
    private Long emotionDiaryId;
    private String title;
    private String content;
    private Long memberId;
    private List<String> tags;
}
