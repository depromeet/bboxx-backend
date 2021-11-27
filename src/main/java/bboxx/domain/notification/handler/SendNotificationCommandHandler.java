package bboxx.domain.notification.handler;

import bboxx.domain.CommandHandler;
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

public class SendNotificationCommandHandler implements CommandHandler<SendNotificationCommand, Notification> {

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

        String message = translator.translateNotificationMessage(command.getEmotionCreatedTime(), pushToken.getOwnerNickname(), "ko", command.getEmotionDiaryTitle());
        Notification notification = new Notification(
                command.getReceiverId(),
                command.getEmotionDiaryId(),
                "과거의 너로부터 도착한 일기",
                message,
                NotificationState.SENT
        );

        notificationRepository.save(notification);

        pushNotifier.notify(pushToken, notification);

        return notification;
    }
}
