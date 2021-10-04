package bboxx.application.controller;

import bboxx.application.controller.dto.request.SignInRequest;
import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.SignInResponse;
import bboxx.application.service.member.MemberService;
import bboxx.application.service.member.command.SignInCommand;
import bboxx.domain.member.SocialProviderType;
import bboxx.infrastructure.jwt.JwtProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ApiResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
        String token = memberService.signIn(new SignInCommand(request.getProviderType(), request.getAuthData()));
        return ApiResponse.success(new SignInResponse(token));
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
