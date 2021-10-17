package bboxx.infrastructure.repository;

import bboxx.domain.notification.Notification;
import bboxx.domain.notification.commandmodel.NotificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends NotificationRepository, JpaRepository<Notification, Long> {

}
