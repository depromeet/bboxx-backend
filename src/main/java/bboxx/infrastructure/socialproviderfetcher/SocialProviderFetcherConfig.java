package bboxx.infrastructure.socialproviderfetcher;

import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialProviderFetcherConfig {

    @Bean
    public ProviderUserFetcher fetchProviderUser() {
        KakaoFetcher fetchKakaoUser = new KakaoFetcher();
        AppleFetcher appleUserClient = new AppleFetcher();
        GoogleFetcher fetchGoogleUser = new GoogleFetcher();
        SocialProviderFetcherProxy client = new SocialProviderFetcherProxy();
        client.addFetcher(SocialProviderType.KAKAO, fetchKakaoUser);
        client.addFetcher(SocialProviderType.APPLE, appleUserClient);
        client.addFetcher(SocialProviderType.GOOGLE, fetchGoogleUser);
        return client;
    }
}
