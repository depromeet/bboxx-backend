package bboxx.domain.notification.handler;

import bboxx.domain.notification.FakePushTokenRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RegisterPushTokenCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class RegisterPushTokenCommandHandlerTest {

    @Test
    public void 기존에_토큰이_있다면_토큰정보만_변경한다() {
        // given
        FakePushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        Long ownerId = 123L;
        PushToken pushToken = new PushToken(ownerId, "existed_nicknames", null, PushTokenState.ENABLED);
        pushTokenRepository.save(pushToken);
        RegisterPushTokenCommand command = new RegisterPushTokenCommand(ownerId, "nicknames", "token");

        RegisterPushTokenCommandHandler sut = new RegisterPushTokenCommandHandler(pushTokenRepository);

        // when
        PushToken actual = sut.handle(command);

        // then
        assertThat(actual.getToken()).isEqualTo("token");
        assertThat(actual.getOwnerNickname()).isEqualTo("existed_nicknames");
        assertThat(actual.getState()).isEqualTo(PushTokenState.ENABLED);
    }

    @Test
    public void 기존에_토큰이_없다면_새로운_토큰정보_및_닉네임을_저장한다() {
        // given
        FakePushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        RegisterPushTokenCommand command = new RegisterPushTokenCommand(123L, "nicknames", "token");

        RegisterPushTokenCommandHandler sut = new RegisterPushTokenCommandHandler(pushTokenRepository);

        // when
        PushToken actual = sut.handle(command);

        // then
        assertThat(actual.getToken()).isEqualTo("token");
        assertThat(actual.getOwnerNickname()).isEqualTo("nicknames");
        assertThat(actual.getState()).isEqualTo(PushTokenState.ENABLED);
    }
}