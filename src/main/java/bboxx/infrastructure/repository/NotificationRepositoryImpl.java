package bboxx.infrastructure.repository;

import bboxx.domain.notification.Notification;
import bboxx.domain.notification.commandmodel.NotificationRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class NotificationRepositoryImpl extends SimpleJpaRepository<Notification, Long> implements NotificationRepository {

    public NotificationRepositoryImpl(EntityManager entityManager) {
        super(Notification.class, entityManager);
    }

    @Override
    public Notification save(Notification notification) {
        return super.save(notification);
    }
}
