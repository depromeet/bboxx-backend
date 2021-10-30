package bboxx.domain.emotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    @ApiModelProperty(value = "감정 id", example = "1")
    private Long id;

    @Column
    @ApiModelProperty(value = "감정 상태 문구", example = "울고싶어")
    private String status;

    @Column(length = 512)
    @ApiModelProperty(value = "감정 이미지 URL", example = "url")
    private String emotionUrl;

}
