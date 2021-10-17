package bboxx.domain.notification.commandmodel;

import bboxx.domain.notification.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);
}
