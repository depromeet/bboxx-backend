package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.emotion.GetAllGrowthDiaryInMonthQueryHandler;
import bboxx.application.service.emotion.KeepGrowthDiaryCommandHandler;
import bboxx.domain.emotion.command.KeepGrowthDiaryCommand;
import bboxx.domain.emotion.query.GetAllGrowthDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.GrowthDiaryView;
import bboxx.domain.notification.NotificationState;
import bboxx.domain.notification.query.GetAllNotificationQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "감정 일기 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/growth-diaries")
@Slf4j
public class GrowthDiaryController {

    private final KeepGrowthDiaryCommandHandler keepGrowthDiaryCommandHandler;
    private final GetAllGrowthDiaryInMonthQueryHandler getAllGrowthDiaryInMonthQueryHandler;

    @ApiOperation(value = "성장 일기 쓰기")
    @PostMapping("/keep")
    public ApiResponse<EmptyJsonResponse> keepGrowthDiary(@RequestBody KeepGrowthDiaryCommand command,
                                                             @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        keepGrowthDiaryCommandHandler.handle(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "성장 일기 가져오기")
    @GetMapping("")
    public ApiResponse<List<GrowthDiaryView>> getAllGrowthDiaryInMonth(@RequestParam(value = "member_id") Long memberId,
                                                                          @RequestParam(value = "year") int year,
                                                                          @RequestParam(value = "month") int month,
                                                                          @AuthenticationPrincipal AuthUserDetail userDetail) {
        GetAllGrowthDiaryInMonthQuery query = new GetAllGrowthDiaryInMonthQuery(memberId, year, month);
        log.info("getAllGrowthDiaryInMonth request, authId: {}, query: {}", userDetail.getId(), query);
        userDetail.validateSameUser(memberId);
        return ApiResponse.success(getAllGrowthDiaryInMonthQueryHandler.handle(query));
    }
}
