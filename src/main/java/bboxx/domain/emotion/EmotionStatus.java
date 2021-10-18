package bboxx.domain.emotion;

import lombok.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class EmotionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String status;

    @Column(columnDefinition = "TEXT")
    private String emotionPath;

}
