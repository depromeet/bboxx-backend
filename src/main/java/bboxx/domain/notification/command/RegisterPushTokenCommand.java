package bboxx.domain.notification.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterPushTokenCommand {
    Long ownerId;
    String nickname;
    String token;
}
