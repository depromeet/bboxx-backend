package bboxx.domain.notification;

import bboxx.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long emotionId;

    @Column
    private String title;

    @Column
    private String message;

    public Notification(Long id, Long memberId, Long emotionId, String title, String message) {
        this.id = id;
        this.memberId = memberId;
        this.emotionId = emotionId;
        this.title = title;
        this.message = message;
    }
}
