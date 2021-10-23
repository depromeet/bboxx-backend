package bboxx.infrastructure.firebase;

import bboxx.domain.notification.Notification;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.commandmodel.PushNotifier;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class FcmNotifier implements PushNotifier {

    @Override
    public void notify(PushToken token, Notification notification) {

        if (token.getToken() == null || token.getState().equals(PushTokenState.DISABLED)) {
            log.info("this member is not allowed receive push, receiverId: {}, emotionDiaryId: {}, pushTokenState: {}", notification.getReceiverId(), notification.getEmotionDiaryId(), token.getState());
            return;
        }

        Message message = makeMessage(token.getToken(), null, notification.getMessage(), Map.of("emotionDiaryId", notification.getId().toString()));
        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("success to send push message, response: {} ", response);
        } catch (Exception e) {
            // noti 를 못보내도 에러는 아니라고 판단했다.
            log.error("failed to send push message", e);
        }
    }

    private Message makeMessage(String targetToken, String title, String body, Map<String, String> data) {
        return Message.builder()
                .setToken(targetToken)
                .setNotification(com.google.firebase.messaging.Notification
                        .builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setAndroidConfig(AndroidConfig
                        .builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .setApnsConfig(ApnsConfig
                        .builder()
                        .setAps(Aps
                                .builder()
                                .setSound("default")
                                .setContentAvailable(true)
                                .build())
                        .putHeader("apns-priority", "10")
                        .build())
                .putAllData(data)
                .build();
    }
}
