package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.service.emotion.EmotionService;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommandInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "감정 일기 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emotions")
public class EmotionController {

    private final EmotionService emotionService;

    @ApiOperation(value = "감정 일기 등록 전 정보 요청")
    @GetMapping("/")
    public ApiResponse<CreateEmotionDiaryCommandInfo> createEmotionDiaryInfo() {
        return ApiResponse.success(emotionService.createInfo());
    }

    @ApiOperation(value = "감정 일기 등록 요청")
    @PostMapping("/")
    public ApiResponse<EmptyJsonResponse> createEmotionDiary(@RequestBody CreateEmotionDiaryCommand command) {
        emotionService.create(command);
        return ApiResponse.success();
    }
}
