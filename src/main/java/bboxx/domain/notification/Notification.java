package bboxx.domain.notification;

import bboxx.domain.BaseTimeEntity;
import bboxx.domain.notification.commandmodel.NotificationTranslator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long receiverId;

    @Column
    private Long emotionId;

    @Column
    private String title;

    @Column
    private String message;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private NotificationState state;

    public Notification(Long id, Long receiverId, Long emotionId, String title, String message, NotificationState state) {
        this.id = id;
        this.receiverId = receiverId;
        this.emotionId = emotionId;
        this.title = title;
        this.message = message;
        this.state = state;
    }

    public Notification(Long receiverId, Long emotionId, String title, String message, NotificationState state) {
        this.receiverId = receiverId;
        this.emotionId = emotionId;
        this.title = title;
        this.message = message;
        this.state = state;
    }
}
