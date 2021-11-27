package bboxx.domain.decibel.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetDecibelQuery {
    @ApiModelProperty(value = "decibel 수치", example = "77")
    private Long decibel;

}
