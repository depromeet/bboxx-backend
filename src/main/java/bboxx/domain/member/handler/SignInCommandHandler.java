package bboxx.domain.member.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.command.SignInCommand;
import bboxx.domain.member.command.SignInCommandResult;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;

public class SignInCommandHandler implements CommandHandler<SignInCommand, SignInCommandResult> {

    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final TokenGenerator tokenGenerator;

    public SignInCommandHandler(ProviderUserFetcher providerUserFetcher, MemberRepository memberRepository, TokenGenerator tokenGenerator) {
        this.providerUserFetcher = providerUserFetcher;
        this.memberRepository = memberRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public SignInCommandResult handle(SignInCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        return this.memberRepository.findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType())
                .map(member -> tokenGenerator.generateToken(Long.toString(member.getId()), member.getNickname()))
                .map(SignInCommandResult::new)
                .orElse(new SignInCommandResult());
    }
}
