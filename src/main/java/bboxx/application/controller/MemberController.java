package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.NicknameGenerationApiData;
import bboxx.application.service.member.MemberService;
import bboxx.domain.member.commandmodel.NicknameGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "회원정보 api")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final NicknameGenerator nicknameGenerator;

    public MemberController(MemberService memberService, NicknameGenerator nicknameGenerator) {
        this.memberService = memberService;
        this.nicknameGenerator = nicknameGenerator;
    }

    @ApiOperation(value = "유저 등록 요청")
    @PostMapping("/api/v1/generate-member-nickname")
    public ApiResponse<NicknameGenerationApiData> generateMemberNickname() {
        NicknameGenerationApiData data = new NicknameGenerationApiData(nicknameGenerator.generate());
        return ApiResponse.success(data);
    }

//    @ApiOperation(value = "특정 회원의 정보를 가져온다.")
//    @GetMapping("/api/v1/members/{memberId}")
//    public ApiResponse<void> getMemberById(@PathVariable String memberId) {
//
//    }


}

