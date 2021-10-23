package bboxx.domain.emotion;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="emotion_diary_id")
    private List<GrowthDiary> growthDiaries;

    public void sendNotification() {
        this.isNotiSent = true;
    }

    public void keepGrowthDiary(String title, String content, Long memberId, List<String> tags) {
        GrowthDiary growthDiary = new GrowthDiary(title, content, memberId, this.id, tags);
        this.growthDiaries.add(growthDiary);
    }
}
