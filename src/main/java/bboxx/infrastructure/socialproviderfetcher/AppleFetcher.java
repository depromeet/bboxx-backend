package bboxx.infrastructure.socialproviderfetcher;

import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.SocialProviderType;

public class AppleFetcher implements ProviderUserFetcher {
    @Override
    public SocialProvider fetch(SocialProviderType providerType, String authData) {
        return new SocialProvider(SocialProviderType.APPLE, "apple-" + authData);
    }
}