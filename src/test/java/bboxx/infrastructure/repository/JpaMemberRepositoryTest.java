package bboxx.infrastructure.repository;

import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.querymodel.NotificationReader;
import bboxx.infrastructure.repository.readmodel.NotificationReaderImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JpaMemberRepository")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class JpaMemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Nested
    class findByProviderIdAndProviderType {

        @Test
        void providerId_와_providerType_이_일치한다면_member_를_반환한다() {
            // given
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
            MemberRepositoryImpl memberRepository = new MemberRepositoryImpl(entityManager, jpaQueryFactory);

            String providerId = "1234abc";
            SocialProviderType providerType = SocialProviderType.GOOGLE;
            Member member = new Member(11L, "bboxxibal", MemberState.ACTIVE, new SocialProvider(providerType, providerId));
            memberRepository.save(member);

            // when
            Optional<Member> result = memberRepository.findByProviderIdAndProviderType(providerId, providerType);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getProviderId()).isEqualTo(providerId);
            assertThat(result.get().getProviderType()).isEqualTo(providerType);
        }

        @Test
        void providerId_와_providerType_이_일치하지_않는다면_빈값을_반환한다() {
            // given
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
            MemberRepositoryImpl memberRepository = new MemberRepositoryImpl(entityManager, jpaQueryFactory);

            String providerId = "1234abc";
            SocialProviderType providerType = SocialProviderType.GOOGLE;
            Member member = new Member(11L, "bboxxibal", MemberState.ACTIVE, new SocialProvider(providerType, providerId));
            memberRepository.save(member);

            // when
            Optional<Member> result = memberRepository.findByProviderIdAndProviderType(providerId+"123", providerType);

            // then
            assertThat(result).isEmpty();
        }
    }
}
