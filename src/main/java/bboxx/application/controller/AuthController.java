package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.service.member.MemberCommandService;
import bboxx.domain.member.command.SignInCommand;
import bboxx.domain.member.command.SignInCommandResult;
import bboxx.domain.member.command.SignUpCommand;
import bboxx.domain.member.command.SignUpCommandResult;
import bboxx.infrastructure.jwt.JwtProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인증 api")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberCommandService memberService;
    private final JwtProvider jwtProvider;

    public AuthController(MemberCommandService memberCommandService, JwtProvider jwtProvider) {
        this.memberService = memberCommandService;
        this.jwtProvider = jwtProvider;
    }

    @ApiOperation(value = "로그인 요청")
    @PostMapping("/signin")
    public ApiResponse<SignInCommandResult> signIn(@RequestBody SignInCommand command) {
        return ApiResponse.success(memberService.signIn(command));
    }

    @ApiOperation(value = "유저 등록 요청")
    @PostMapping("/signup")
    public ApiResponse<SignUpCommandResult> signUp(@RequestBody SignUpCommand command) {
        return ApiResponse.success(memberService.signUp(command));
    }

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
