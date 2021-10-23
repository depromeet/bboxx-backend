package bboxx.domain.notification.querymodel;

import bboxx.domain.notification.query.GetAllNotificationQuery;

import java.util.List;

public interface NotificationReader {
    List<NotificationView> findAllByQuery(GetAllNotificationQuery query);
}
