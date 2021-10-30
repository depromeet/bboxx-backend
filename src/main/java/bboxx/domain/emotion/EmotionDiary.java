package bboxx.domain.emotion;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
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
    private List<ImprovementDiary> improvementDiaries;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "es_emotion_diary_id")
    private List<EmotionStatus> emotionStatusList;

    protected EmotionDiary() {

    }

    public EmotionDiary(String title, String content, Long memberId, Long categoryId, String emotionStatuses) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.emotionStatuses = emotionStatuses;
    }

    public EmotionDiary(String title, String content, Long memberId, Long categoryId, String emotionStatuses, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.emotionStatuses = emotionStatuses;
        this.createdAt = createdAt;
    }

    public EmotionDiary(String title, String content, Long memberId, Long categoryId, List<Emotion> emotionList) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.emotionStatusList = new ArrayList<>();

        for (Emotion emotion : emotionList) {
            EmotionStatus emotionStatus = new EmotionStatus(this.id, emotion);
            this.emotionStatusList.add(emotionStatus);
        }
    }

    public void sendNotification() {
        this.isNotiSent = true;
    }

    public void keepImprovementDiary(String title, String content, Long memberId, List<String> tags) {
        ImprovementDiary improvementDiary = new ImprovementDiary(title, content, memberId, this.id, tags);
        this.improvementDiaries.add(improvementDiary);
    }
}
