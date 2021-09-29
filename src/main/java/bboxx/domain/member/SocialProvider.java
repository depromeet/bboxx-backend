package bboxx.domain.member;

import lombok.Getter;

@Getter
public class SocialProvider {

    private SocialProviderType socialProviderType;

    private String providerId;

    public SocialProvider(SocialProviderType socialProviderType, String providerId) {
        this.socialProviderType = socialProviderType;
        this.providerId = providerId;
    }
}
