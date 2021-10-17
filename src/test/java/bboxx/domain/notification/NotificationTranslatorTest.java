package bboxx.domain.notification;

import bboxx.infrastructure.translator.SimpleTranslator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NotificationTranslator")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class NotificationTranslatorTest {

    @Test
    public void 특정개월전의_메시지를_번역한다() {
        // given
        LocalDateTime createdTime = LocalDateTime.now().minusMonths(2);
        String nickname = "닉네임입니다.";
        String language = "ko";

        SimpleTranslator translator = new SimpleTranslator();

        // when
        String result = translator.translateNotificationMessage(createdTime, nickname, language);

        // then
        assertThat(result).contains(nickname);
        assertThat(result).contains("2개월 전");
    }
}
