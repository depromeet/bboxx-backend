package bboxx.infrastructure.socialproviderfetcher.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name ="kakaoApiClient", url = "https://kapi.kakao.com")
public interface KakaoUserClient {

    @GetMapping("/v2/user/me")
    KakaoUser getProfileInfo(@RequestHeader("Authorization") String accessToken);

}