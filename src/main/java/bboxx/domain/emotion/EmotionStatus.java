package bboxx.domain.emotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class EmotionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "es_emotion_diary_id")
    private Long emotionDiaryId;

    @ManyToOne
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;

    public EmotionStatus() {

    }

    public EmotionStatus(Long emotionDiaryId, Emotion emotion) {
        this.emotionDiaryId = emotionDiaryId;
        this.emotion = emotion;
    }

}
