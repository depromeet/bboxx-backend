package bboxx.application.service.member;

import bboxx.application.service.member.command.SignInCommand;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.commandmodel.GetOrCreateMemberService;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.infrastructure.jwt.JwtProvider;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {

    private final ProviderUserFetcher providerUserFetcher;
    private final GetOrCreateMemberService getOrCreateMemberService;
    private final JwtProvider jwtProvider;

    public MemberService(ProviderUserFetcher providerUserFetcher, GetOrCreateMemberService getOrCreateMemberService, JwtProvider jwtProvider) {
        this.providerUserFetcher = providerUserFetcher;
        this.getOrCreateMemberService = getOrCreateMemberService;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public String signIn(SignInCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());
        Member member = this.getOrCreateMemberService.execute(socialProvider);

        // token 생성
        return jwtProvider.generateToken(Long.toString(member.getId()), member.getNickname());
    }
}
