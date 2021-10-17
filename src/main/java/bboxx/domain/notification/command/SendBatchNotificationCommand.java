package bboxx.domain.notification.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SendBatchNotificationCommand {
    LocalDateTime startDate;
    LocalDateTime endDate;
}
