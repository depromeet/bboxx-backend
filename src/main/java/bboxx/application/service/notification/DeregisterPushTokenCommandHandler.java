package bboxx.application.service.notification;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class DeregisterPushTokenCommandHandler {

    private final PushTokenRepository pushTokenRepository;

    public DeregisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository) {
        this.pushTokenRepository = pushTokenRepository;
    }

    public PushToken handle(DeregisterPushTokenCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), null, PushTokenState.DISABLED));

        pushToken.deregisterToken();

        return pushTokenRepository.save(pushToken);
    }
}
