package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.decibel.DecibelFacade;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.query.GetDecibelQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "빡침 측정 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decibel")
public class DecibelController {

    private final DecibelFacade decibelFacade;

    @ApiOperation(value = "데시벨 측정 기록 등록", notes = "데시벨 측정 후 해당 데시벨의 정보를 저장하는 API 입니다. 여기서 반환되는 값인 decibel id는 추후 특정 데시벨에 대한 정보를 확인할 때 활용할 수 있습니다.")
    @PostMapping("")
    public ApiResponse<CreateDecibelCommandResult> createDecibel(@RequestBody CreateDecibelCommand command,
                                                                 @ApiIgnore  @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        return ApiResponse.success(decibelFacade.create(command));
    }

    @ApiOperation(value = "특정 데시벨 기록 조회", notes = "특정 데시벨에 대한 정보를 반환하는 API 입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "decibelId", value = "데시벨 고유 id", required = true, dataType = "Long", paramType = "path", defaultValue = "1"),
    })
    @GetMapping("/{decibelId}")
    public ApiResponse<GetDecibelQuery> findDecibel(@PathVariable Long decibelId) {
        return ApiResponse.success(decibelFacade.findDecibel(decibelId));
    }
}
