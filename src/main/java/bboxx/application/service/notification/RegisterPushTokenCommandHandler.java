package bboxx.application.service.notification;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterPushTokenCommandHandler {

    private final PushTokenRepository pushTokenRepository;

    public RegisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository) {
        this.pushTokenRepository = pushTokenRepository;
    }

    public PushToken handle(RegisterPushTokenCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), command.getToken(), PushTokenState.ENABLED));

        pushToken.registerToken(command.getToken());

        return pushTokenRepository.save(pushToken);
    }
}
