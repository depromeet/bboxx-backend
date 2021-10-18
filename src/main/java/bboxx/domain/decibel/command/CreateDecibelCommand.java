package bboxx.domain.decibel.command;

import bboxx.domain.decibel.Decibel;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateDecibelCommand {

    private Long decibel;

    private Long memberId;

    public Decibel toEntity() {
        return Decibel.builder()
                .decibel(decibel)
                .memberId(memberId)
                .build();
    }
}
