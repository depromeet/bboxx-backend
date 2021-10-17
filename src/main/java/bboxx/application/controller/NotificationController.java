package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.notification.DeregisterPushTokenCommandHandler;
import bboxx.application.service.notification.RegisterPushTokenCommandHandler;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "알람 api")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final RegisterPushTokenCommandHandler registerPushTokenCommandHandler;
    private final DeregisterPushTokenCommandHandler deregisterPushTokenCommandHandler;

    @ApiOperation(value = "push 토큰을 등록한다")
    @PostMapping("/register-push-token")
    public ApiResponse<PushToken> registerPushToken(@RequestBody RegisterPushTokenCommand command,
                                                    @AuthenticationPrincipal AuthUserDetail userDetail
    ) {
        log.info("registerPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(registerPushTokenCommandHandler.handle(command));
    }

    @ApiOperation(value = "push 토큰을 등록 해제한다.")
    @PostMapping("/deregister-push-token")
    public ApiResponse<PushToken> deregisterPushToken(@RequestBody DeregisterPushTokenCommand command,
                                                      @AuthenticationPrincipal AuthUserDetail userDetail
    ) {
        log.info("deregisterPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(deregisterPushTokenCommandHandler.handle(command));
    }
}
