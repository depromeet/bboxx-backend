package bboxx.infrastructure.socialproviderfetcher.kakao;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(value = "kakao_fetcher")
public class KakaoFetcher implements ProviderUserFetcher {

    private final KakaoUserClient kakaoUserClient;

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String authData) {
        try {
            KakaoUser kakaoUser = kakaoUserClient.getProfileInfo("Bearer " + authData);
            return SocialProvider.builder()
                    .providerId(kakaoUser.getId())
                    .providerType(providerType)
                    .nickname(kakaoUser.getProperties().getNickname())
                    .profileImage(kakaoUser.getProperties().getProfileImage())
                    .build();
        } catch (FeignException e) {
            if (e.status() == 401) {
                throw new DomainException(DomainErrorCode.UNAUTHORIZED_ERROR);
            }
            throw e;
        }
    }
}
