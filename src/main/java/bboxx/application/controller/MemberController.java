package bboxx.application.controller;

import bboxx.application.controller.dto.request.UpdateMemberRequest;
import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.NicknameGenerationApiData;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.member.MemberCommandService;
import bboxx.application.service.member.MemberQueryService;
import bboxx.domain.member.command.UpdateMemberCommand;
import bboxx.domain.member.query.GetMemberOneQuery;
import bboxx.domain.member.commandmodel.NicknameGenerator;
import bboxx.domain.member.querymodel.MemberView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "회원정보 api")
@RestController
@Slf4j
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final NicknameGenerator nicknameGenerator;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService, NicknameGenerator nicknameGenerator) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
        this.nicknameGenerator = nicknameGenerator;
    }

    @ApiOperation(value = "닉네임 생성 요청", notes = "랜덤한 닉네임을 생성한 후 반환하는 API 입니다.")
    @PostMapping("/api/v1/generate-member-nickname")
    public ApiResponse<NicknameGenerationApiData> generateMemberNickname() {
        NicknameGenerationApiData data = new NicknameGenerationApiData(nicknameGenerator.generate());
        return ApiResponse.success(data);
    }

    @ApiOperation(value = "특정 회원 정보 업데이트", notes = "특정 회원이 정보 수정을 요청하면, 해당 정보로 수정 후 저장하는 API 입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "특정 회원의 고유 id", required = true, dataType = "Long", paramType = "path", defaultValue = "1"),
    })
    @PutMapping("/api/v1/members/{memberId}")
    public ApiResponse<MemberView> updateMember(@PathVariable Long memberId,
                                                @RequestBody UpdateMemberRequest request,
                                                @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {

        userDetail.validateSameUser(memberId);

        memberCommandService.updateMember(new UpdateMemberCommand(memberId, request.getNickname()));

        MemberView memberView = memberQueryService.getMemberOne(new GetMemberOneQuery(memberId));

        return ApiResponse.success(memberView);
    }

    @ApiOperation(value = "특정 회원 정보 가져오기", notes = "특정 회원의 정보를 반환하는 API 입니다.")
    @GetMapping("/api/v1/members/{memberId}")
    public ApiResponse<MemberView> getMemberById(@PathVariable Long memberId) {
        MemberView memberView = memberQueryService.getMemberOne(new GetMemberOneQuery(memberId));
        return ApiResponse.success(memberView);
    }
}

