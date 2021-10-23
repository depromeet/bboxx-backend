package bboxx.application.controller;

import bboxx.application.controller.dto.request.UpdateMemberRequest;
import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.NicknameGenerationApiData;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.member.MemberCommandService;
import bboxx.application.service.member.MemberQueryService;
import bboxx.domain.member.command.UpdateMemberCommand;
import bboxx.domain.member.commandmodel.NicknameGenerator;
import bboxx.domain.member.query.GetMemberOneQuery;
import bboxx.domain.member.querymodel.MemberView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "내정보 api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MeController {

    private final MemberQueryService memberQueryService;

    @ApiOperation(value = "내 정보를 가져온다.")
    @GetMapping("/api/v1/me")
    public ApiResponse<MemberView> getMemberById(@AuthenticationPrincipal AuthUserDetail userDetail) {
        MemberView memberView = memberQueryService.getMemberOne(new GetMemberOneQuery(userDetail.getId()));
        return ApiResponse.success(memberView);
    }
}

