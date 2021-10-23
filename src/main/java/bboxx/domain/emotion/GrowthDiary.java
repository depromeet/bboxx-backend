package bboxx.domain.emotion;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "emotion_diary_id")
    private Long emotionDiaryId;

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

        for (String tag : tags) {
            GrowthDiaryTag growthDiaryTag = new GrowthDiaryTag(tag);
            this.growthDiaryTags.add(growthDiaryTag);
        }
    }
}
