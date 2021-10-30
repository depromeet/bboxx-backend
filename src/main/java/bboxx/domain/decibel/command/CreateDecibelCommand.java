package bboxx.domain.decibel.command;

import bboxx.domain.decibel.Decibel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateDecibelCommand {
    @ApiModelProperty(value = "decibel 수치", example = "77")
    private Long decibel;
    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long memberId;

    public Decibel toEntity() {
        return Decibel.builder()
                .decibel(decibel)
                .memberId(memberId)
                .build();
    }
}
