package bboxx.infrastructure.repository;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.domain.notification.querymodel.PushTokenReader;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static bboxx.domain.notification.QNotification.notification;
import static bboxx.domain.notification.QPushToken.pushToken;

@Repository
public class PushTokenRepositoryImpl extends SimpleJpaRepository<PushToken, Long> implements PushTokenRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PushTokenRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(PushToken.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PushToken save(PushToken pushToken) {
        return super.save(pushToken);
    }

    @Override
    public Optional<PushToken> findByOwnerId(Long ownerId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(pushToken)
                        .from(pushToken)
                        .where(pushToken.ownerId.eq(ownerId))
                        .fetchOne()
        );
    }
}
