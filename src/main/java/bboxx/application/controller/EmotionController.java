package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.emotion.EmotionDiaryService;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.query.GetEmotionInfoCommand;
import bboxx.domain.emotion.command.FindEmotionDiaryCommandResult;
import io.swagger.annotations.Api;
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

    @ApiOperation(value = "감정 일기 등록 전 정보 요청")
    @GetMapping("")
    public ApiResponse<GetEmotionInfoCommand> getEmotionInfo() {
        return ApiResponse.success(emotionDiaryService.getEmotionInfo());
    }

    @ApiOperation(value = "감정 일기 등록 요청")
    @PostMapping("")
    public ApiResponse<EmptyJsonResponse> createEmotionDiary(@RequestBody CreateEmotionDiaryCommand command){
//                                                             @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {
//        userDetail.validateSameUser(command.getMemberId());
        emotionDiaryService.createEmotionDiary(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "감정 일기 조회 요청")
    @GetMapping("/{emotionId}")
    public ApiResponse<FindEmotionDiaryCommandResult> findEmotionDiary(@PathVariable Long emotionId) {
        return ApiResponse.success(emotionDiaryService.findEmotionDiary(emotionId));
    }

    @ApiOperation(value = "감정 일기 삭제 요청")
    @DeleteMapping("/{emotionId}")
    public ApiResponse<EmptyJsonResponse> deleteEmotionDiary(@PathVariable Long emotionId) {
        emotionDiaryService.deleteEmotionDiary(emotionId);
        return ApiResponse.success();
    }
}
