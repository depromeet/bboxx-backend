package bboxx.infrastructure.repository;

import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m LEFT JOIN SocialProvider p ON m.id = p.member.id WHERE p.providerId = :providerId AND p.providerType = :providerType")
    Optional<Member> findByProviderIdAndProviderType(String providerId, SocialProviderType providerType);
}
