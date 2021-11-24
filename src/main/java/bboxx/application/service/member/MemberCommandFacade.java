package bboxx.application.service.member;

import bboxx.domain.member.command.*;
import bboxx.domain.member.handler.SignInCommandHandler;
import bboxx.domain.member.handler.SignUpCommandHandler;
import bboxx.domain.member.handler.UpdateMemberCommandHandler;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class MemberCommandFacade {

    private final SignInCommandHandler signInCommandHandler;
    private final SignUpCommandHandler signUpCommandHandler;
    private final UpdateMemberCommandHandler updateMemberCommandHandler;

    private final PushTokenRepository pushTokenRepository;

    @Transactional
    public SignInCommandResult signIn(SignInCommand command) {
        return signInCommandHandler.handle(command);
    }

    @Transactional
    public SignUpCommandResult signUp(SignUpCommand command) {
        return signUpCommandHandler.handle(command);
    }

    @Transactional
    public void updateMember(UpdateMemberCommand command) {
        Long memberId = updateMemberCommandHandler.handle(command);

        pushTokenRepository.findByOwnerId(memberId)
                .ifPresent(token -> token.changeNickname(command.getNickname()));
    }
}
