package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.emotion.KeepGrowthDiaryCommandHandler;
import bboxx.domain.emotion.command.KeepGrowthDiaryCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "감정 일기 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/growth-diaries")
public class GrowthDiaryController {

    private final KeepGrowthDiaryCommandHandler keepGrowthDiaryCommandHandler;

    @ApiOperation(value = "성장 일기 쓰기")
    @PostMapping("/keep")
    public ApiResponse<EmptyJsonResponse> keepGrowthDiary(@RequestBody KeepGrowthDiaryCommand command,
                                                             @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        keepGrowthDiaryCommandHandler.handle(command);
        return ApiResponse.success();
    }
}
