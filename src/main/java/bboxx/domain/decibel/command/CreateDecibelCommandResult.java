package bboxx.domain.decibel.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDecibelCommandResult {
    @ApiModelProperty(value = "decibel 고유 id", example = "1")
    private Long id;
}
