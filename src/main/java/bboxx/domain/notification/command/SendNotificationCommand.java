package bboxx.domain.notification.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SendNotificationCommand {
    Long receiverId;
    Long emotionDiaryId;
    String emotionDiaryTitle;
    LocalDateTime emotionCreatedTime;
}
