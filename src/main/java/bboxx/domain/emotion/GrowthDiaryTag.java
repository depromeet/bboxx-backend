package bboxx.domain.emotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@ToString
public class GrowthDiaryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;

    @Column(name = "growth_diary_id")
    private Long growthDiaryId;

    protected GrowthDiaryTag() {

    }

    public GrowthDiaryTag(String tag) {
        this.tag = tag;
    }
}
