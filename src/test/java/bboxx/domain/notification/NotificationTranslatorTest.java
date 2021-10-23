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
        String title = "퇴사했다!";

        SimpleTranslator translator = new SimpleTranslator();

        // when
        String result = translator.translateNotificationMessage(createdTime, nickname, language, title);

        // then
        assertThat(result).contains(nickname);
        assertThat(result).contains(title);
    }

    @Test
    public void 글자수가_46자_를_넘어가면_dotdotdot_을_반환한다() {
        // given
        LocalDateTime createdTime = LocalDateTime.now().minusMonths(2);
        String nickname = "닉네임입니다.";
        String language = "ko";
        String title = "퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!퇴사했다!";

        SimpleTranslator translator = new SimpleTranslator();

        // when
        String result = translator.translateNotificationMessage(createdTime, nickname, language, title);

        // then
        assertThat(result).contains(nickname);
        assertThat(result.substring(result.length() - 3)).contains("...");
    }
}
