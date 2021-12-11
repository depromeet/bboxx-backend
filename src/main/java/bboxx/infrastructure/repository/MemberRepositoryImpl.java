package bboxx.infrastructure.repository;

import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static bboxx.domain.member.QMember.member;
import static bboxx.domain.member.QSocialProvider.socialProvider;

@Repository
public class MemberRepositoryImpl extends SimpleJpaRepository<Member, Long> implements MemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(Member.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Member save(Member member) {
        return super.save(member);
    }

//    @Query("SELECT m FROM Member m LEFT JOIN SocialProvider p ON m.id = p.member.id WHERE p.providerId = :providerId AND p.providerType = :providerType")
    @Override
    public Optional<Member> findByProviderIdAndProviderType(String providerId, SocialProviderType providerType) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(member)
                        .join(member.provider, socialProvider)
                        .where(
                                socialProvider.providerId.eq(providerId),
                                socialProvider.providerType.eq(providerType)
                        )
                        .fetchOne()
        );

    }
}
