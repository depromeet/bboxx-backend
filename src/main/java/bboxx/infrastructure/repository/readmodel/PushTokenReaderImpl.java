package bboxx.infrastructure.repository.readmodel;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.querymodel.PushTokenReader;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static bboxx.domain.notification.QPushToken.pushToken;

@Repository
public class PushTokenReaderImpl implements PushTokenReader {

    private final JPAQueryFactory jpaQueryFactory;

    public PushTokenReaderImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
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
