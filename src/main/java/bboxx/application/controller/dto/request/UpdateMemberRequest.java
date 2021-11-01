package bboxx.application.controller.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberRequest {
    @ApiModelProperty(value = "닉네임", example = "사랑스러운딸기")
    private String nickname;
}
