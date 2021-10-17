package bboxx.application.service.notification;

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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SendNotificationCommandHandler {

    private final NotificationRepository notificationRepository;
    private final PushTokenRepository pushTokenRepository;
    private final NotificationTranslator translator;
    private final PushNotifier pushNotifier;

    public SendNotificationCommandHandler(NotificationRepository notificationRepository, PushTokenRepository pushTokenRepository, NotificationTranslator translator, PushNotifier pushNotifier) {
        this.notificationRepository = notificationRepository;
        this.pushTokenRepository = pushTokenRepository;
        this.translator = translator;
        this.pushNotifier = pushNotifier;
    }

    public Notification handle(SendNotificationCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getReceiverId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));

        // Todo 감정일기의 owner 확인 및 notification 전송했다고 변경 필요

        // Fixme LocalDateTime 부분 감정일기의 시간으로 변경 필요.
        String message = translator.translateNotificationMessage(LocalDateTime.now().minusWeeks(3), pushToken.getOwnerNickname(), "ko");
        Notification notification = new Notification(
                command.getReceiverId(),
                command.getEmotionId(),
                null,
                message,
                NotificationState.SENT
        );

        notificationRepository.save(notification);

        pushNotifier.notify(pushToken, notification);

        return notification;
    }
}
