package bboxx.domain.member.commandmodel;

import bboxx.domain.member.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOrCreateMemberService {

    private final MemberRepository memberRepository;
    private final NicknameGenerator nicknameGenerator;

    public GetOrCreateMemberService(MemberRepository memberRepository, NicknameGenerator nicknameGenerator) {
        this.memberRepository = memberRepository;
        this.nicknameGenerator = nicknameGenerator;
    }

    public Member execute(SocialProvider socialProvider) {
        Optional<Member> existedMember = this.memberRepository
                .findByProviderProviderIdAndProviderProviderType(socialProvider.getProviderId(), socialProvider.getProviderType());
        if (existedMember.isPresent()) {
            return existedMember.get();
        }
        Member newMember = new Member(this.nicknameGenerator.generate(), socialProvider);
        return memberRepository.save(newMember);
    }
}

