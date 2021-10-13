package bboxx.application.service.member;

import bboxx.application.service.member.command.*;
import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.commandmodel.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {

    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final MemberCreator memberCreator;
    private final TokenGenerator tokenGenerator;

    public MemberService(ProviderUserFetcher providerUserFetcher, MemberRepository memberRepository, MemberCreator memberCreator, TokenGenerator tokenGenerator) {
        this.providerUserFetcher = providerUserFetcher;
        this.memberRepository = memberRepository;
        this.memberCreator = memberCreator;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional
    public SignInCommandResult signIn(SignInCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        return this.memberRepository.findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType())
                .map(member -> tokenGenerator.generateToken(Long.toString(member.getId()), member.getNickname()))
                .map(SignInCommandResult::new)
                .orElse(new SignInCommandResult());
    }

    @Transactional
    public SignUpCommandResult signUp(SignUpCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        Member member = memberCreator.execute(socialProvider, command.getNickname());
        memberRepository.save(member);

        String token = tokenGenerator.generateToken(Long.toString(member.getId()), member.getNickname());
        return new SignUpCommandResult(token);
    }
}
