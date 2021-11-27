package bboxx.application.config;

import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.domain.notification.handler.RegisterPushTokenCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class NotificationConfiguration {

    private final PushTokenRepository pushTokenRepository;

    @Bean
    public RegisterPushTokenCommandHandler registerPushTokenCommandHandler() {
        return new RegisterPushTokenCommandHandler(pushTokenRepository);
    }
}
