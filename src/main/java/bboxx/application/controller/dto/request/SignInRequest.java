package bboxx.application.controller.dto.request;

import bboxx.domain.member.SocialProviderType;
import lombok.Getter;

@Getter
public class SignInRequest {
    private SocialProviderType providerType;
    private String authData;
}
