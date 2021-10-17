package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterPushTokenCommandHandler {

    private final PushTokenRepository pushTokenRepository;
    private final MemberRepository memberRepository;

    public RegisterPushTokenCommandHandler(PushTokenRepository pushTokenRepository, MemberRepository memberRepository) {
        this.pushTokenRepository = pushTokenRepository;
        this.memberRepository = memberRepository;
    }

    public PushToken handle(RegisterPushTokenCommand command) {

        Member member = memberRepository.findById(command.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        PushToken pushToken = pushTokenRepository.findByOwnerId(command.getOwnerId())
                .orElse(new PushToken(command.getOwnerId(), member.getNickname(), command.getToken(), PushTokenState.ENABLED));

        pushToken.registerToken(command.getToken());

        return pushTokenRepository.save(pushToken);
    }
}
