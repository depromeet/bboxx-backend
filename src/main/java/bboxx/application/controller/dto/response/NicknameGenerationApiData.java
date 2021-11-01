package bboxx.application.controller.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameGenerationApiData {
    @ApiModelProperty(value = "닉네임", example = "사랑스러운딸기")
    private String nickname;
}
