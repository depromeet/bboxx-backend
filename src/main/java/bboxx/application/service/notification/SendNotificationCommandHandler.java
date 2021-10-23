package bboxx.application.service.notification;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.Notification;
import bboxx.domain.notification.NotificationState;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.SendNotificationCommand;
import bboxx.domain.notification.commandmodel.NotificationRepository;
import bboxx.domain.notification.commandmodel.NotificationTranslator;
import bboxx.domain.notification.commandmodel.PushNotifier;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.infrastructure.repository.JpaEmotionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SendNotificationCommandHandler {

    private final NotificationRepository notificationRepository;
    private final PushTokenRepository pushTokenRepository;
    private final JpaEmotionRepository emotionRepository;
    private final NotificationTranslator translator;
    private final PushNotifier pushNotifier;

    public SendNotificationCommandHandler(NotificationRepository notificationRepository, PushTokenRepository pushTokenRepository, JpaEmotionRepository emotionRepository, NotificationTranslator translator, PushNotifier pushNotifier) {
        this.notificationRepository = notificationRepository;
        this.pushTokenRepository = pushTokenRepository;
        this.emotionRepository = emotionRepository;
        this.translator = translator;
        this.pushNotifier = pushNotifier;
    }

    public Notification handle(SendNotificationCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getReceiverId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));

        EmotionDiary emotionDiary = emotionRepository.findById(command.getEmotionDiaryId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));
        emotionDiary.sendNotification();

        String message = translator.translateNotificationMessage(emotionDiary.getCreatedAt(), pushToken.getOwnerNickname(), "ko", emotionDiary.getTitle());
        Notification notification = new Notification(
                command.getReceiverId(),
                command.getEmotionDiaryId(),
                null,
                message,
                NotificationState.SENT
        );

        notificationRepository.save(notification);

        pushNotifier.notify(pushToken, notification);

        return notification;
    }
}
