package bboxx.application.service.notification;

import bboxx.application.controller.dto.request.DeregisterPushTokenRequest;
import bboxx.application.controller.dto.request.RegisterPushTokenRequest;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.handler.DeregisterPushTokenCommandHandler;
import bboxx.domain.notification.handler.RegisterPushTokenCommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class NotificationCommandFacade {

    RegisterPushTokenCommandHandler registerPushTokenCommandHandler;
    DeregisterPushTokenCommandHandler deregisterPushTokenCommandHandler;

    private final MemberRepository memberRepository;

    @Transactional
    public PushToken registerPushToken(RegisterPushTokenRequest request) {

        Member member = memberRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        return registerPushTokenCommandHandler.handle(new RegisterPushTokenCommand(
                request.getOwnerId(),
                member.getNickname(),
                request.getToken()
        ));
    }

    @Transactional
    public PushToken deregisterPushToken(DeregisterPushTokenRequest request) {

        Member member = memberRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        return deregisterPushTokenCommandHandler.handle(new DeregisterPushTokenCommand(
                request.getOwnerId(),
                member.getNickname()
        ));
    }
}
