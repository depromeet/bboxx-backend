package bboxx.infrastructure.socialproviderfetcher;

import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.infrastructure.socialproviderfetcher.apple.AppleFetcher;
import bboxx.infrastructure.socialproviderfetcher.google.GoogleFetcher;
import bboxx.infrastructure.socialproviderfetcher.kakao.KakaoFetcher;
import bboxx.infrastructure.socialproviderfetcher.kakao.KakaoUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
public class SocialProviderFetcherConfiguration {

    private final AppleFetcher appleFetcher;

    private final KakaoFetcher kakaoFetcher;

    private final GoogleFetcher googleFetcher;

    @Bean
    @Primary
    public ProviderUserFetcher fetchProviderUser() {
        SocialProviderFetcherProxy client = new SocialProviderFetcherProxy();
        client.addFetcher(SocialProviderType.APPLE, appleFetcher);
        client.addFetcher(SocialProviderType.KAKAO, kakaoFetcher);
        client.addFetcher(SocialProviderType.GOOGLE, googleFetcher);
        return client;
    }
}
