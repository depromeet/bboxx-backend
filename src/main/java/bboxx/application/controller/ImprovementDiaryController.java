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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "성장 일기 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/improvement-diaries")
@Slf4j
public class ImprovementDiaryController {

    private final KeepImprovementDiaryCommandHandler keepImprovementDiaryCommandHandler;
    private final GetAllImprovementDiaryInMonthQueryHandler getAllImprovementDiaryInMonthQueryHandler;

    @ApiOperation(value = "성장 일기 쓰기", notes = "성장 일기 작성 후 정보를 저장하는 API 입니다.")
    @PostMapping("/keep")
    public ApiResponse<EmptyJsonResponse> keepImprovementDiary(@RequestBody KeepImprovementDiaryCommand command,
                                                               @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {
        userDetail.validateSameUser(command.getMemberId());
        keepImprovementDiaryCommandHandler.handle(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "성장 일기 가져오기", notes = "특정 성장 일기에 대한 정보를 반환하는 API 입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_id", value = "사용자 고유 id", required = true, dataType = "Long", paramType = "path", defaultValue = "1"),
            @ApiImplicitParam(name = "year", value = "년도", required = true, dataType = "int", paramType = "path", defaultValue = "2021"),
            @ApiImplicitParam(name = "month", value = "월", required = true, dataType = "int", paramType = "path", defaultValue = "10"),
    })
    @GetMapping("")
    public ApiResponse<List<ImprovementDiaryView>> getAllImprovementDiaryInMonth(@RequestParam(value = "member_id") Long memberId,
                                                                                 @RequestParam(value = "year") int year,
                                                                                 @RequestParam(value = "month") int month,
                                                                                 @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {
        GetAllImprovementDiaryInMonthQuery query = new GetAllImprovementDiaryInMonthQuery(memberId, year, month);
        log.info("getAllImprovementDiaryInMonth request, authId: {}, query: {}", userDetail.getId(), query);
        userDetail.validateSameUser(memberId);
        return ApiResponse.success(getAllImprovementDiaryInMonthQueryHandler.handle(query));
    }
}
