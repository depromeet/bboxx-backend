package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.notification.DeregisterPushTokenCommandHandler;
import bboxx.application.service.notification.GetPushTokenOneQueryHandler;
import bboxx.application.service.notification.RegisterPushTokenCommandHandler;
import bboxx.application.service.notification.SendNotificationCommandHandler;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.command.SendNotificationCommand;
import bboxx.domain.notification.query.GetPushTokenOneQuery;
import bboxx.domain.notification.querymodel.PushTokenView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "알람 api")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final RegisterPushTokenCommandHandler registerPushTokenCommandHandler;
    private final DeregisterPushTokenCommandHandler deregisterPushTokenCommandHandler;
    private final GetPushTokenOneQueryHandler getPushTokenOneQueryHandler;
    private final SendNotificationCommandHandler sendNotificationCommandHandler;

    @ApiOperation(value = "push 토큰을 등록한다")
    @PostMapping("/register-push-token")
    public ApiResponse<PushToken> registerPushToken(@RequestBody RegisterPushTokenCommand command,
                                                    @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("registerPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(registerPushTokenCommandHandler.handle(command));
    }

    @ApiOperation(value = "push 토큰을 등록 해제한다.")
    @PostMapping("/deregister-push-token")
    public ApiResponse<PushToken> deregisterPushToken(@RequestBody DeregisterPushTokenCommand command,
                                                      @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("deregisterPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(deregisterPushTokenCommandHandler.handle(command));
    }

    @ApiOperation(value = "여러 노티를 전송한다.")
    @PostMapping("/send-batch-notification")
    public ApiResponse<EmptyJsonResponse> sendBatchNotification() {
//        log.info("registerPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
//        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success();
    }

    @ApiOperation(value = "특정 감정일기에 대한 노티를 전송한다.")
    @PostMapping("/send-notification")
    public ApiResponse<EmptyJsonResponse> sendNotification(@RequestBody SendNotificationCommand command,
                                                          @AuthenticationPrincipal AuthUserDetail userDetail) {

        userDetail.validateSameUser(command.getReceiverId());
        sendNotificationCommandHandler.handle(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "push 토큰정보를 가져온다.")
    @GetMapping("/push-tokens/{ownerId}")
    public ApiResponse<PushTokenView> getPushTokenOne(@PathVariable Long ownerId,
                                                      @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("getPushTokenOne request, authId: {}, ownerId: {}", userDetail.getId(), ownerId);
        userDetail.validateSameUser(ownerId);
        GetPushTokenOneQuery query = new GetPushTokenOneQuery(ownerId);
        return ApiResponse.success(getPushTokenOneQueryHandler.handle(query));
    }
}
