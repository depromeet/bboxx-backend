package bboxx.application.service.member;

import bboxx.application.service.member.command.SignInCommand;
import bboxx.application.service.member.command.SignInCommandResult;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.commandmodel.GetOrCreateMemberService;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.infrastructure.jwt.JwtProvider;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MemberService {

    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(ProviderUserFetcher providerUserFetcher, MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.providerUserFetcher = providerUserFetcher;
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public SignInCommandResult signIn(SignInCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        return this.memberRepository.findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType())
                .map(member -> jwtProvider.generateToken(Long.toString(member.getId()), member.getNickname()))
                .map(SignInCommandResult::new)
                .orElse(new SignInCommandResult());

    }
}
