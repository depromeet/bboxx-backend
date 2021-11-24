package bboxx.application.service.member;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.command.*;
import bboxx.domain.member.commandmodel.MemberCreator;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;
import bboxx.domain.member.handler.SignInCommandHandler;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class MemberCommandService {

    private final SignInCommandHandler signInCommandHandler;
    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final MemberCreator memberCreator;
    private final TokenGenerator tokenGenerator;

    private final PushTokenRepository pushTokenRepository;

    @Transactional
    public SignInCommandResult signIn(SignInCommand command) {
        return signInCommandHandler.handle(command);
    }

    @Transactional
    public SignUpCommandResult signUp(SignUpCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        Member member = memberCreator.execute(socialProvider, command.getNickname());
        memberRepository.save(member);

        String token = tokenGenerator.generateToken(Long.toString(member.getId()), member.getNickname());
        return new SignUpCommandResult(token);
    }

    @Transactional
    public void updateMember(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        member.updateInfo(command.getNickname());

        pushTokenRepository.findByOwnerId(command.getMemberId())
                .ifPresent(token -> token.changeNickname(command.getNickname()));
    }
}
