package bboxx.application.service.member.command;

import bboxx.domain.member.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpCommand {
    private SocialProviderType providerType;
    private String authData;
    private String nickname;
}
