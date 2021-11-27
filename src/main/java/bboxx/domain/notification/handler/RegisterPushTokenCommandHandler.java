package bboxx.domain.notification.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;

public class RegisterPushTokenCommandHandler implements CommandHandler<RegisterPushTokenCommand, PushToken> {

    private final PushTokenRepository pushTokenRepository;

    public RegisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository) {
        this.pushTokenRepository = pushTokenRepository;
    }

    public PushToken handle(RegisterPushTokenCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), command.getNickname(), command.getToken(), PushTokenState.ENABLED));

        pushToken.registerToken(command.getToken());

        return pushTokenRepository.save(pushToken);
    }
}
