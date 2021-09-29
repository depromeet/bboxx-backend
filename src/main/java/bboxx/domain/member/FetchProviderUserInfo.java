package bboxx.domain.member;

public interface FetchProviderUserInfo {
    ProviderUserInfo fetch(SocialProviderType providerType);
}
