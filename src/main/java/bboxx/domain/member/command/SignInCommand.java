package bboxx.domain.member.command;

import bboxx.domain.member.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInCommand {
    private SocialProviderType providerType;
    private String authData;
}
