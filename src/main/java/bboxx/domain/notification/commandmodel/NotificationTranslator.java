package bboxx.domain.notification.commandmodel;

import java.time.LocalDateTime;

public interface NotificationTranslator {
    String translateNotificationMessage(LocalDateTime emotionCreatedTime, String nickname, String language, String title);
}
