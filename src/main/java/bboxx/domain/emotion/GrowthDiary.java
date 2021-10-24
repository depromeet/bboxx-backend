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
public class GrowthDiary extends BaseTimeEntity {

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

//    @ManyToOne()
//    @JoinColumn(name = "emotion_diary_id", nullable = false)
//    private EmotionDiary emotionDiary;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="growth_diary_id")
    private List<GrowthDiaryTag> growthDiaryTags;

    protected GrowthDiary() {
    }

    public GrowthDiary(String title, String content, Long memberId, Long emotionDiaryId, List<String> tags) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.emotionDiaryId = emotionDiaryId;
        this.growthDiaryTags = new ArrayList<>();
        this.keptAt = LocalDateTime.now();

        for (String tag : tags) {
            GrowthDiaryTag growthDiaryTag = new GrowthDiaryTag(tag);
            this.growthDiaryTags.add(growthDiaryTag);
        }
    }

    public GrowthDiary(String title, String content, Long memberId, Long emotionDiaryId, List<String> tags, LocalDateTime keptAt) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.emotionDiaryId = emotionDiaryId;
        this.growthDiaryTags = new ArrayList<>();
        this.keptAt = keptAt;

        for (String tag : tags) {
            GrowthDiaryTag growthDiaryTag = new GrowthDiaryTag(tag);
            this.growthDiaryTags.add(growthDiaryTag);
        }
    }
}
