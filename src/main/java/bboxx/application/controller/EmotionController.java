package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.emotion.EmotionDiaryService;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.query.GetEmotionInfoCommand;
import bboxx.domain.emotion.querymodel.EmotionDiaryView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "감정 일기 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emotions")
public class EmotionController {

    private final EmotionDiaryService emotionDiaryService;

    @ApiOperation(value = "감정 일기 등록 전 정보 요청", notes = "감정 일기 작성 시 필요한 감정에 대한 정보를 반환하는 API 입니다.")
    @GetMapping("")
    public ApiResponse<GetEmotionInfoCommand> getEmotionInfo() {
        return ApiResponse.success(emotionDiaryService.getEmotionInfo());
    }

    @ApiOperation(value = "감정 일기 작성", notes = "감정 일기 작성 후 정보를 저장하는 API 입니다.")
    @PostMapping("")
    public ApiResponse<EmptyJsonResponse> createEmotionDiary(@RequestBody CreateEmotionDiaryCommand command,
                                                             @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        emotionDiaryService.createEmotionDiary(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "감정 일기 조회", notes = "특정 감정 일기에 대한 정보를 반환하는 API 입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emotionId", value = "감정 일기 고유 id", required = true, dataType = "Long", paramType = "path", defaultValue = "1"),
    })
    @GetMapping("/{emotionId}")
    public ApiResponse<EmotionDiaryView> findEmotionDiary(@PathVariable Long emotionId) {
        return ApiResponse.success(emotionDiaryService.findEmotionDiary(emotionId));
    }

    @ApiOperation(value = "감정 일기 삭제", notes = "특정 감정 일기에 대한 정보를 삭제하는 API 입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emotionId", value = "감정 일기 고유 id", required = true, dataType = "Long", paramType = "path", defaultValue = "1"),
    })
    @DeleteMapping("/{emotionId}")
    public ApiResponse<EmptyJsonResponse> deleteEmotionDiary(@PathVariable Long emotionId) {
        emotionDiaryService.deleteEmotionDiary(emotionId);
        return ApiResponse.success();
    }
}
