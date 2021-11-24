package bboxx.application.service.member;

import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;
import bboxx.domain.member.handler.SignInCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MemberConfiguration {

    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final TokenGenerator tokenGenerator;

    @Bean
    public SignInCommandHandler signInCommandHandler() {
        return new SignInCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);
    }
}
