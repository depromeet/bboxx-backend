package bboxx.domain.notification.querymodel;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.notification.NotificationState;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class NotificationView {
    private Long id;
    private Long receiverId;
    private EmotionDiary emotionDiary;
    private String title;
    private String message;
    private NotificationState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotificationView() {
    }
}
