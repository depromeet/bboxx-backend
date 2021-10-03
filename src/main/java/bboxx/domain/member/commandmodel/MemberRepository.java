package bboxx.domain.member.commandmodel;

import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProviderType;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByProviderIdAndProviderType(String providerId, SocialProviderType providerType);
}
