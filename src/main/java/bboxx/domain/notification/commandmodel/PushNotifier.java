package bboxx.domain.notification.commandmodel;

import bboxx.domain.notification.Notification;
import bboxx.domain.notification.PushToken;

public interface PushNotifier {
    void notify(PushToken token, Notification notification);
}
