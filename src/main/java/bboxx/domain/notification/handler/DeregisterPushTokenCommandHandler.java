package bboxx.domain.notification.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;

public class DeregisterPushTokenCommandHandler implements CommandHandler<DeregisterPushTokenCommand, PushToken> {

    private final PushTokenRepository pushTokenRepository;

    public DeregisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository) {
        this.pushTokenRepository = pushTokenRepository;
    }

    public PushToken handle(DeregisterPushTokenCommand command) {

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), command.getNickname(), null, PushTokenState.DISABLED));

        pushToken.deregisterToken();

        return pushTokenRepository.save(pushToken);
    }
}
