package bboxx.domain.emotion;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@ToString
public class EmotionDiary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 1500)
    private String content;

    @Column
    private Long memberId;

    @Column
    private Long categoryId;

    @Column
    private String emotionStatuses;

    @Column
    private Boolean isNotiSent;

    public void sendNotification() {
        this.isNotiSent = true;
    }
}
