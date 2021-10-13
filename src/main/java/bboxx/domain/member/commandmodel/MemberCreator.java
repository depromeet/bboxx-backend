package bboxx.domain.member.commandmodel;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberCreator {

    private final MemberRepository memberRepository;

    public MemberCreator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member execute(SocialProvider socialProvider, String nickname) {
        Optional<Member> existedMember = this.memberRepository
                .findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType());
        if (existedMember.isPresent()) {
            throw new DomainException(DomainErrorCode.MEMBER_EXISTED_ERROR);
        }

        return new Member(nickname, MemberState.ACTIVE, socialProvider);
    }
}
