package bboxx.domain.emotion.querymodel;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EmotionStatusView {
    private Long id;
    private String status;
    private String emotionUrl;

    public EmotionStatusView(Long id, String status, String emotionUrl) {
        this.id = id;
        this.status = status;
        this.emotionUrl = emotionUrl;
    }
}
