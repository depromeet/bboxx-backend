package bboxx.domain.member.commandmodel;

import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;

public interface ProviderUserFetcher {
    SocialProvider fetch(SocialProviderType providerType, String authData);
}
