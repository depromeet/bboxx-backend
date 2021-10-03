package bboxx.infrastructure.socialproviderfetcher;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;

import java.util.EnumMap;

public class SocialProviderFetcherProxy implements ProviderUserFetcher {

    private final EnumMap<SocialProviderType, ProviderUserFetcher> fetchers = new EnumMap<>(SocialProviderType.class);

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String authData) {
        if (fetchers.get(providerType) != null) {
            return fetchers.get(providerType).fetch(providerType, authData);
        }
        throw new DomainException(DomainErrorCode.SOCIAL_USER_FETCH_ERROR);
    }

    public void addFetcher(SocialProviderType providerType, ProviderUserFetcher fetcher) {
        this.fetchers.put(providerType, fetcher);
    }
}
