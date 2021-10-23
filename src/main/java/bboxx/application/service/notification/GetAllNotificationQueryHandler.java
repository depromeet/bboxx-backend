package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.query.GetAllNotificationQuery;
import bboxx.domain.notification.querymodel.NotificationReader;
import bboxx.domain.notification.querymodel.NotificationView;
import bboxx.domain.notification.querymodel.PushTokenView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllNotificationQueryHandler {

    private final NotificationReader notificationReader;

    public GetAllNotificationQueryHandler(NotificationReader notificationReader) {
        this.notificationReader = notificationReader;
    }

    public List<NotificationView> handle(GetAllNotificationQuery query) {
        return notificationReader.findAllByQuery(query);
    }
}
