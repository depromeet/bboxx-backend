package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class DeregisterPushTokenCommandHandler {

    private final PushTokenRepository pushTokenRepository;
    private final MemberRepository memberRepository;

    public DeregisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository, MemberRepository memberRepository) {
        this.pushTokenRepository = pushTokenRepository;
        this.memberRepository = memberRepository;
    }

    public PushToken handle(DeregisterPushTokenCommand command) {

        Member member = memberRepository.findById(command.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), member.getNickname(), null, PushTokenState.DISABLED));

        pushToken.deregisterToken();

        return pushTokenRepository.save(pushToken);
    }
}
