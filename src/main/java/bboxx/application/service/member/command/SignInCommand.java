package bboxx.application.service.member.command;

import bboxx.domain.member.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInCommand {
    SocialProviderType providerType;
    String authData;
}
