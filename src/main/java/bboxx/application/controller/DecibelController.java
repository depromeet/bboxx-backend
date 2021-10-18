package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.decibel.DecibelService;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.command.FindDecibelCommandResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "빡침 측정 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decibel")
public class DecibelController {

    private final DecibelService decibelService;

    @ApiOperation(value = "데시벨 측정 기록 등록 요청")
    @PostMapping("/")
    public ApiResponse<CreateDecibelCommandResult> createDecibel(@RequestBody CreateDecibelCommand command,
                                                                 @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        return ApiResponse.success(decibelService.create(command));
    }

    @ApiOperation(value = "특정 데시벨 기록 정보 요청")
    @GetMapping("/{decibelId}")
    public ApiResponse<FindDecibelCommandResult> findDecibel(@PathVariable Long decibelId) {
        return ApiResponse.success(decibelService.findDecibel(decibelId));
    }
}
