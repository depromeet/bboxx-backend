package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import bboxx.domain.notification.query.GetPushTokenOneQuery;
import bboxx.domain.notification.querymodel.PushTokenReader;
import bboxx.domain.notification.querymodel.PushTokenView;
import lombok.extern.slf4j.Slf4j;
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

@DisplayName("GetPushTokenOneQueryHandler")
@ExtendWith(MockitoExtension.class)
@Slf4j
class GetPushTokenOneQueryHandlerTest {

    @Mock
    PushTokenReader pushTokenReader;

    @Test
    public void push_token_이_존재하지_않는다면_NOT_FOUND_에러를_반환한다() {
        // given
        Long ownerId = 123L;

        given(pushTokenReader.findByOwnerId(any()))
                .willReturn(Optional.empty());
        GetPushTokenOneQuery query = new GetPushTokenOneQuery(ownerId);
        GetPushTokenOneQueryHandler handler = new GetPushTokenOneQueryHandler(pushTokenReader);

        // when
        // then
        DomainException result = assertThrows(DomainException.class, () -> {
            handler.handle(query);
        });
        assertThat(result).isEqualTo(new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));
    }

    @Test
    public void push_token_존재한다면_tokenView를_반환한다() {
        // given
        Long ownerId = 123L;
        PushToken pushToken = new PushToken(123L, ownerId, "this_is_push_token!!", PushTokenState.ENABLED);

        given(pushTokenReader.findByOwnerId(any()))
                .willReturn(Optional.of(pushToken));

        GetPushTokenOneQuery query = new GetPushTokenOneQuery(ownerId);
        GetPushTokenOneQueryHandler handler = new GetPushTokenOneQueryHandler(pushTokenReader);

        // when
        PushTokenView result = handler.handle(query);

        // then
        assertThat(result.getId()).isEqualTo(pushToken.getId());
        assertThat(result.getOwnerId()).isEqualTo(pushToken.getOwnerId());
        assertThat(result.getState()).isEqualTo(pushToken.getState());
    }
}