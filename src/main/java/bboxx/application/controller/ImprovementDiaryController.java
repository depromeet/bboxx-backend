package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.emotion.GetAllImprovementDiaryInMonthQueryHandler;
import bboxx.application.service.emotion.KeepImprovementDiaryCommandHandler;
import bboxx.domain.emotion.command.KeepImprovementDiaryCommand;
import bboxx.domain.emotion.query.GetAllImprovementDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.ImprovementDiaryView;
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
@RequestMapping("/api/v1/improvement-diaries")
@Slf4j
public class ImprovementDiaryController {

    private final KeepImprovementDiaryCommandHandler keepImprovementDiaryCommandHandler;
    private final GetAllImprovementDiaryInMonthQueryHandler getAllImprovementDiaryInMonthQueryHandler;

    @ApiOperation(value = "성장 일기 쓰기")
    @PostMapping("/keep")
    public ApiResponse<EmptyJsonResponse> keepImprovementDiary(@RequestBody KeepImprovementDiaryCommand command,
                                                             @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        keepImprovementDiaryCommandHandler.handle(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "성장 일기 가져오기")
    @GetMapping("")
    public ApiResponse<List<ImprovementDiaryView>> getAllImprovementDiaryInMonth(@RequestParam(value = "member_id") Long memberId,
                                                                                 @RequestParam(value = "year") int year,
                                                                                 @RequestParam(value = "month") int month,
                                                                                 @AuthenticationPrincipal AuthUserDetail userDetail) {
        GetAllImprovementDiaryInMonthQuery query = new GetAllImprovementDiaryInMonthQuery(memberId, year, month);
        log.info("getAllImprovementDiaryInMonth request, authId: {}, query: {}", userDetail.getId(), query);
        userDetail.validateSameUser(memberId);
        return ApiResponse.success(getAllImprovementDiaryInMonthQueryHandler.handle(query));
    }
}
