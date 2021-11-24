package bboxx.domain.member.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.command.SignUpCommand;
import bboxx.domain.member.command.SignUpCommandResult;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;

import java.util.Optional;

public class SignUpCommandHandler implements CommandHandler<SignUpCommand, SignUpCommandResult> {
    private final ProviderUserFetcher providerUserFetcher;
    private final MemberRepository memberRepository;
    private final TokenGenerator tokenGenerator;

    public SignUpCommandHandler(ProviderUserFetcher providerUserFetcher, MemberRepository memberRepository, TokenGenerator tokenGenerator) {
        this.providerUserFetcher = providerUserFetcher;
        this.memberRepository = memberRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public SignUpCommandResult handle(SignUpCommand command) {
        SocialProvider socialProvider = this.providerUserFetcher.fetch(command.getProviderType(), command.getAuthData());

        Optional<Member> existedMember = this.memberRepository
                .findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType());
        if (existedMember.isPresent()) {
            throw new DomainException(DomainErrorCode.MEMBER_EXISTED_ERROR);
        }

        Member member = new Member(command.getNickname(), MemberState.ACTIVE, socialProvider);
        memberRepository.save(member);

        String token = tokenGenerator.generateToken(Long.toString(member.getId()), member.getNickname());
        return new SignUpCommandResult(token);
    }
}