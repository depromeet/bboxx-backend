package bboxx.application.controller;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.application.security.AuthUserDetail;
import bboxx.application.service.notification.*;
import bboxx.domain.notification.NotificationState;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.command.SendNotificationCommand;
import bboxx.domain.notification.query.GetAllNotificationQuery;
import bboxx.domain.notification.query.GetPushTokenOneQuery;
import bboxx.domain.notification.querymodel.NotificationView;
import bboxx.domain.notification.querymodel.PushTokenView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final GetAllNotificationQueryHandler getAllNotificationQueryHandler;

    @ApiOperation(value = "push 토큰을 등록한다")
    @PostMapping("/push-tokens/register-push-token")
    public ApiResponse<PushToken> registerPushToken(@RequestBody RegisterPushTokenCommand command,
                                                    @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("registerPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(registerPushTokenCommandHandler.handle(command));
    }

    @ApiOperation(value = "push 토큰을 등록 해제한다.")
    @PostMapping("/push-tokens/deregister-push-token")
    public ApiResponse<PushToken> deregisterPushToken(@RequestBody DeregisterPushTokenCommand command,
                                                      @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("deregisterPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success(deregisterPushTokenCommandHandler.handle(command));
    }

    @ApiOperation(value = "push 토큰정보를 가져온다.")
    @GetMapping("/push-tokens/{ownerId}")
    public ApiResponse<PushTokenView> getPushTokenOne(@PathVariable Long ownerId,
                                                      @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {

        log.info("getPushTokenOne request, authId: {}, ownerId: {}", userDetail.getId(), ownerId);
        userDetail.validateSameUser(ownerId);
        GetPushTokenOneQuery query = new GetPushTokenOneQuery(ownerId);
        return ApiResponse.success(getPushTokenOneQueryHandler.handle(query));
    }

    @ApiOperation(value = "여러 노티를 전송한다.")
    @PostMapping("/notifications/send-batch-notification")
    public ApiResponse<EmptyJsonResponse> sendBatchNotification() {
//        log.info("registerPushToken request, authId: {}, ownerId: {}", userDetail.getId(), command.getOwnerId());
//        userDetail.validateSameUser(command.getOwnerId());
        return ApiResponse.success();
    }

    @ApiOperation(value = "특정 감정일기에 대한 노티를 전송한다.")
    @PostMapping("/notifications/send-notification")
    public ApiResponse<EmptyJsonResponse> sendNotification(@RequestBody SendNotificationCommand command,
                                                           @ApiIgnore  @AuthenticationPrincipal AuthUserDetail userDetail) {

        userDetail.validateSameUser(command.getReceiverId());
        sendNotificationCommandHandler.handle(command);
        return ApiResponse.success();
    }

    @ApiOperation(value = "알람정보들을 가져온다.")
    @GetMapping("/notifications")
    public ApiResponse<List<NotificationView>> getNotifications(@RequestParam(value = "receiver_id") Long receiverId,
                                                                @RequestParam(value = "cursor_id", required = false) Long cursorId,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "50") Long limit,
                                                                @ApiIgnore @AuthenticationPrincipal AuthUserDetail userDetail) {

        GetAllNotificationQuery query = new GetAllNotificationQuery(receiverId, cursorId, List.of(NotificationState.SENT), limit);
        log.info("getNotifications request, authId: {}, query: {}", userDetail.getId(), query);
        userDetail.validateSameUser(receiverId);
        return ApiResponse.success(getAllNotificationQueryHandler.handle(query));
    }
}
