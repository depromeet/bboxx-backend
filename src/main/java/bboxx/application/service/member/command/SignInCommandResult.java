package bboxx.application.service.member.command;

import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInCommandResult {
    private String jwt;

    public SignInCommandResult() {

    }
}
