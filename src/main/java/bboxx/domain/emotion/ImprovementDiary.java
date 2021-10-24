package bboxx.domain.emotion;

import bboxx.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Entity
@ToString
public class ImprovementDiary extends BaseTimeEntity {

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
    private LocalDateTime keptAt;

    @Column(name = "emotion_diary_id")
    private Long emotionDiaryId;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="improvement_diary_id")
    private List<ImprovementDiaryTag> tags;

    protected ImprovementDiary() {
    }

    public ImprovementDiary(String title, String content, Long memberId, Long emotionDiaryId, List<String> tags) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.emotionDiaryId = emotionDiaryId;
        this.tags = new ArrayList<>();
        this.keptAt = LocalDateTime.now();

        for (String tag : tags) {
            ImprovementDiaryTag improvementDiaryTag = new ImprovementDiaryTag(tag);
            this.tags.add(improvementDiaryTag);
        }
    }

    public ImprovementDiary(String title, String content, Long memberId, Long emotionDiaryId, List<String> tags, LocalDateTime keptAt) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.emotionDiaryId = emotionDiaryId;
        this.tags = new ArrayList<>();
        this.keptAt = keptAt;

        for (String tag : tags) {
            ImprovementDiaryTag improvementDiaryTag = new ImprovementDiaryTag(tag);
            this.tags.add(improvementDiaryTag);
        }
    }
}
