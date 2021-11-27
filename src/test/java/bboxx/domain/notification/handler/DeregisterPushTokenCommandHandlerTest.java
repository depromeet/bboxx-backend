package bboxx.domain.notification.handler;

import bboxx.domain.notification.FakePushTokenRepository;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DeregisterPushTokenCommandHandler")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class DeregisterPushTokenCommandHandlerTest {

    @Test
    public void push_token_이_존재하지_않는다면_null_값으로_push_token_을_생성한다() {
        // given
        Long ownerId = 123L;
        String ownerNickname = "닉네임닉네임";

        FakePushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        DeregisterPushTokenCommand command = new DeregisterPushTokenCommand(ownerId, ownerNickname);
        DeregisterPushTokenCommandHandler handler = new DeregisterPushTokenCommandHandler(pushTokenRepository);

        // when
        PushToken result = handler.handle(command);

        // then
        assertThat(result.getToken()).isNull();
        assertThat(result.getOwnerId()).isEqualTo(ownerId);
        assertThat(result.getOwnerNickname()).isEqualTo(ownerNickname);
    }

    @Test
    public void push_token_이_이미_존재한다면_token_값이_null_로_변경된다() {
        // given
        String oldToken = "this_is_old_push_token";
        Long pushTokenId = 123L;
        Long ownerId = 123L;
        String ownerNickname = "닉네임입니다123";
        PushToken pushToken = new PushToken(pushTokenId, ownerId, ownerNickname, oldToken, PushTokenState.ENABLED);

        FakePushTokenRepository pushTokenRepository = new FakePushTokenRepository();
        pushTokenRepository.save(pushToken);

        DeregisterPushTokenCommand command = new DeregisterPushTokenCommand(ownerId, ownerNickname);
        DeregisterPushTokenCommandHandler handler = new DeregisterPushTokenCommandHandler(pushTokenRepository);

        // when
        PushToken result = handler.handle(command);

        // then
        assertThat(result.getToken()).isNull();
        assertThat(result.getOwnerId()).isEqualTo(ownerId);
        assertThat(result.getId()).isEqualTo(pushTokenId);
        assertThat(result.getOwnerNickname()).isEqualTo(ownerNickname);
        assertThat(result.getState()).isEqualTo(PushTokenState.DISABLED);
    }
}
