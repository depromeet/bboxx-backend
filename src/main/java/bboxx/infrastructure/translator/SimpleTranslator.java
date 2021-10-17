package bboxx.infrastructure.translator;

import bboxx.domain.notification.commandmodel.NotificationTranslator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class SimpleTranslator implements NotificationTranslator {

    private static final Map<String, String> DATE_SUFFIX = Map.of(
            ChronoUnit.YEARS.toString(), "년",
            ChronoUnit.MONTHS.toString(), "개월",
            ChronoUnit.WEEKS.toString(), "주",
            ChronoUnit.DAYS.toString(), "일"
    );

    @Override
    public String translateNotificationMessage(LocalDateTime emotionCreatedTime, String nickname, String language) {

        ChronoUnit unit = ChronoUnit.MONTHS;
        long duration = ChronoUnit.MONTHS.between(emotionCreatedTime, LocalDateTime.now());
        if (duration <= 1) {
            unit = ChronoUnit.DAYS;
            duration = ChronoUnit.DAYS.between(emotionCreatedTime, LocalDateTime.now());
        }

        String fullDate = emotionCreatedTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        String date = duration + DATE_SUFFIX.getOrDefault(unit.toString(), "달");
        return nickname + "! " +
                date + " 전" +
                "(" + fullDate + ") " + "에 " +
                "쓴 일기가 도착했어 \uD83D\uDCEC 한번 읽어볼래?";
    }
}
