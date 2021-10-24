package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.member.MemberQueryService;
import bboxx.domain.member.query.GetMemberOneQuery;
import bboxx.domain.member.querymodel.MemberView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "내정보 api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MeController {

    private final MemberQueryService memberQueryService;

    @ApiOperation(value = "내 정보를 가져온다.")
    @GetMapping("/api/v1/me")
    public ApiResponse<MemberView> getMemberById(@ApiIgnore  @AuthenticationPrincipal AuthUserDetail userDetail) {
        MemberView memberView = memberQueryService.getMemberOne(new GetMemberOneQuery(userDetail.getId()));
        return ApiResponse.success(memberView);
    }
}

