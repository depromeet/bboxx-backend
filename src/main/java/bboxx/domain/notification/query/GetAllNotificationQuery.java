package bboxx.domain.notification.query;

import bboxx.domain.notification.NotificationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class GetAllNotificationQuery {
    Long receiverId;
    Long cursorId;
    List<NotificationState> states;
    Long limit;
}
