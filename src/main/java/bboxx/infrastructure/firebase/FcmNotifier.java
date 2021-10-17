package bboxx.infrastructure.firebase;

import bboxx.domain.notification.Notification;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.commandmodel.PushNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FcmNotifier implements PushNotifier {
    @Override
    public void notify(PushToken token, Notification notification) {
        log.info("send fcm, receiverId: {}, title: {}, message: {}", notification.getReceiverId(), notification.getTitle(), notification.getTitle());
    }
}
