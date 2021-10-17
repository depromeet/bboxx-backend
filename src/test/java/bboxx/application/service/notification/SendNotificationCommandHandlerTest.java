package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.Notification;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.SendNotificationCommand;
import bboxx.domain.notification.commandmodel.NotificationRepository;
import bboxx.domain.notification.commandmodel.PushNotifier;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.infrastructure.translator.SimpleTranslator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("SendNotificationCommandHandler")
@ExtendWith(MockitoExtension.class)
public class SendNotificationCommandHandlerTest {

    @Mock
    private PushTokenRepository pushTokenRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private PushNotifier pushNotifier;

    @Test
    public void PushToken_이_없다면_에러를_반환한다() {
        // given
        Long receiverId = 123L;
        Long emotionId = 123L;

        given(pushTokenRepository.findByOwnerId(any()))
                .willReturn(Optional.empty());

        SendNotificationCommand command = new SendNotificationCommand(receiverId, emotionId);
        SendNotificationCommandHandler handler = new SendNotificationCommandHandler(
                notificationRepository,
                pushTokenRepository,
                new SimpleTranslator(),
                pushNotifier
        );

        // when
        // then
        DomainException result = assertThrows(DomainException.class, () -> {
            handler.handle(command);
        });
        assertThat(result).isEqualTo(new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));
    }

    @Test
    public void 정상적으로_성공하면_notification_을_생성한다() {
        // given
        Long receiverId = 123L;
        Long emotionId = 123L;
        PushToken pushToken = new PushToken(11L, receiverId, "닉네임닉네임", "토큰토큰", PushTokenState.ENABLED);

        given(pushTokenRepository.findByOwnerId(any()))
                .willReturn(Optional.of(pushToken));
        given(notificationRepository.save(any()))
                .willReturn(any());

        SendNotificationCommand command = new SendNotificationCommand(receiverId, emotionId);
        SendNotificationCommandHandler handler = new SendNotificationCommandHandler(
                notificationRepository,
                pushTokenRepository,
                new SimpleTranslator(),
                pushNotifier
        );

        // when
        Notification result = handler.handle(command);

        // then
        assertThat(result.getEmotionId()).isEqualTo(receiverId);
        assertThat(result.getMessage()).isNotNull();
        verify(pushNotifier, times(1)).notify(any(), any());
    }
}