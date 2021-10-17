package bboxx.domain.notification.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterPushTokenCommand {
    Long ownerId;
    String token;
}
