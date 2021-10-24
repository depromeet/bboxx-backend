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
public class ImprovementDiaryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;

    @Column(name = "improvement_diary_id")
    private Long improvementDiaryId;

    public ImprovementDiaryTag() {

    }

    public ImprovementDiaryTag(String tag) {
        this.tag = tag;
    }
}
