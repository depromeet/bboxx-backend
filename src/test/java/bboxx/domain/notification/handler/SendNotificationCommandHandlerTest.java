package bboxx.domain.notification.handler;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.*;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("SendNotificationCommandHandler")
@ExtendWith(MockitoExtension.class)
public class SendNotificationCommandHandlerTest {

    @Mock
    private PushNotifier pushNotifier;

    @Test
    public void PushToken_이_없다면_에러를_반환한다() {
        // given
        Long receiverId = 123L;
        Long emotionDiaryId = 123L;
        String emotionDiaryTitle = "감정일기 타이틀입니다.";

        PushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        NotificationRepository notificationRepository = new FakeNotificationRepository();

        SendNotificationCommand command = new SendNotificationCommand(receiverId, emotionDiaryId, emotionDiaryTitle, LocalDateTime.now());
        SendNotificationCommandHandler sut = new SendNotificationCommandHandler(
                notificationRepository,
                pushTokenRepository,
                new SimpleTranslator(),
                pushNotifier
        );

        // when
        // then
        DomainException actual = assertThrows(DomainException.class, () -> {
            sut.handle(command);
        });
        assertThat(actual).isEqualTo(new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));
    }

    @Test
    public void 정상적으로_성공하면_notification_을_생성한다() {
        // given
        Long receiverId = 123L;
        Long emotionDiaryId = 123L;
        String emotionDiaryTitle = "감정일기 타이틀입니다.";
        PushToken pushToken = new PushToken(11L, receiverId, "닉네임닉네임", "토큰토큰", PushTokenState.ENABLED);

        PushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        pushTokenRepository.save(pushToken);

        NotificationRepository notificationRepository = new FakeNotificationRepository();

        SendNotificationCommand command = new SendNotificationCommand(receiverId, emotionDiaryId, emotionDiaryTitle, LocalDateTime.now());

        SendNotificationCommandHandler sut = new SendNotificationCommandHandler(
                notificationRepository,
                pushTokenRepository,
                new SimpleTranslator(),
                pushNotifier
        );

        // when
        Notification actual = sut.handle(command);

        // then
        assertThat(actual.getEmotionDiaryId()).isEqualTo(receiverId);
        assertThat(actual.getMessage()).isNotNull();
        assertThat(actual.getTitle()).isEqualTo("과거의 너로부터 도착한 일기");
        verify(pushNotifier, times(1)).notify(any(), any());
    }
}