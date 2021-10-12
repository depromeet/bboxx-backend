package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.service.member.MemberService;
import bboxx.application.service.member.command.SignInCommand;
import bboxx.application.service.member.command.SignInCommandResult;
import bboxx.infrastructure.jwt.JwtProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인증 api")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    public AuthController(MemberService memberService, JwtProvider jwtProvider) {
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
    }

    @ApiOperation(value = "로그인 요청")
    @PostMapping("/signin")
    public ApiResponse<SignInCommandResult> signIn(@RequestBody SignInCommand command) {
        return ApiResponse.success(memberService.signIn(command));
    }

//    @ApiOperation(value = "유저 등록 요청")
//    @PostMapping("/register")
//    public ApiResponse<SignInCommandResult> signIn(@RequestBody SignInCommand command) {
//        return ApiResponse.success(memberService.signIn(command));
//    }

    @ApiOperation(value = "jwt 테스트 api")
    @GetMapping("/validate-jwt/{jwt}")
    public ApiResponse<Boolean> validateJwt(@PathVariable String jwt) {

        try {
            jwtProvider.verifyToken(jwt);
            return ApiResponse.success(true);
        } catch (Exception e) {
            return ApiResponse.success(false);
        }
    }
}
