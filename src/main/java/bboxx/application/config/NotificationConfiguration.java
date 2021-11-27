package bboxx.application.config;

import bboxx.domain.notification.commandmodel.NotificationRepository;
import bboxx.domain.notification.commandmodel.NotificationTranslator;
import bboxx.domain.notification.commandmodel.PushNotifier;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.domain.notification.handler.DeregisterPushTokenCommandHandler;
import bboxx.domain.notification.handler.RegisterPushTokenCommandHandler;
import bboxx.domain.notification.handler.SendNotificationCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class NotificationConfiguration {

    private final PushTokenRepository pushTokenRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTranslator translator;
    private final PushNotifier pushNotifier;

    @Bean
    public RegisterPushTokenCommandHandler registerPushTokenCommandHandler() {
        return new RegisterPushTokenCommandHandler(pushTokenRepository);
    }

    @Bean
    public DeregisterPushTokenCommandHandler deregisterPushTokenCommandHandler() {
        return new DeregisterPushTokenCommandHandler(pushTokenRepository);
    }

    @Bean
    public SendNotificationCommandHandler sendNotificationCommandHandler() {
        return new SendNotificationCommandHandler(notificationRepository, pushTokenRepository, translator, pushNotifier);
    }
}
