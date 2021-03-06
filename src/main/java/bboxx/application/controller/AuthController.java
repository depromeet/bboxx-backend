package bboxx.application.controller;

import bboxx.application.controller.dto.request.ValidateJwtRequest;
import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.ValidateJwtApiDto;
import bboxx.application.service.member.MemberCommandFacade;
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

    private final MemberCommandFacade memberCommandFacade;
    private final JwtProvider jwtProvider;

    public AuthController(MemberCommandFacade memberCommandFacade, JwtProvider jwtProvider) {
        this.memberCommandFacade = memberCommandFacade;
        this.jwtProvider = jwtProvider;
    }

    @ApiOperation(value = "로그인 요청")
    @PostMapping("/signin")
    public ApiResponse<SignInCommandResult> signIn(@RequestBody SignInCommand command) {
        return ApiResponse.
                success(memberCommandFacade.signIn(command));
    }

    @ApiOperation(value = "유저 등록 요청")
    @PostMapping("/signup")
    public ApiResponse<SignUpCommandResult> signUp(@RequestBody SignUpCommand command) {
        return ApiResponse.success(memberCommandFacade.signUp(command));
    }

    @ApiOperation(value = "jwt 테스트 api")
    @PostMapping("/validate-jwt")
    public ApiResponse<ValidateJwtApiDto> validateJwt(@RequestBody ValidateJwtRequest request) {

        try {
            jwtProvider.verifyToken(request.getJwt());
            return ApiResponse.success(new ValidateJwtApiDto(true));
        } catch (Exception e) {
            return ApiResponse.success(new ValidateJwtApiDto(false));
        }
    }
}
