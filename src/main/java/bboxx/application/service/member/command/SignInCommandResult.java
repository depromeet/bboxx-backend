package bboxx.application.service.member.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInCommandResult {
    private String token;

    public SignInCommandResult() {

    }
}
