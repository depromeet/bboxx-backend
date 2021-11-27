package bboxx.domain.notification;

import bboxx.domain.helper.RandomIdGenerator;
import bboxx.domain.notification.commandmodel.NotificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeNotificationRepository implements NotificationRepository {

    public List<Notification> notifications = new ArrayList<>();

    @Override
    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            Notification newNotification = new Notification(
                    RandomIdGenerator.generate(notifications.stream().map(Notification::getId).collect(Collectors.toList())),
                    notification.getReceiverId(),
                    notification.getEmotionDiaryId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getState()
            );
            this.notifications.add(newNotification);
            return newNotification;
        } else {
            int index = notifications.stream()
                    .map(Notification::getId)
                    .collect(Collectors.toList())
                    .indexOf(notification.getId());
            if (index == -1) {
                notifications.add(notification);
            } else {
                notifications.set(index, notification);
            }
            return notification;
        }
    }

}

