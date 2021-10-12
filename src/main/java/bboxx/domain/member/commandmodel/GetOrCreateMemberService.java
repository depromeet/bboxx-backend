package bboxx.domain.member.commandmodel;

import bboxx.domain.member.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOrCreateMemberService {

    private final MemberRepository memberRepository;

    public GetOrCreateMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member execute(SocialProvider socialProvider) {
        Optional<Member> existedMember = this.memberRepository
                .findByProviderIdAndProviderType(socialProvider.getProviderId(), socialProvider.getProviderType());
        if (existedMember.isPresent()) {
            return existedMember.get();
        }
        Member newMember = new Member(MemberState.CREATED, socialProvider);
        return memberRepository.save(newMember);
    }
}

