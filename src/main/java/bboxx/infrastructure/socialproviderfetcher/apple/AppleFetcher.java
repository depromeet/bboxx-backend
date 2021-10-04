package bboxx.infrastructure.socialproviderfetcher.apple;

import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.SocialProviderType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(value = "apple_fetcher")
public class AppleFetcher implements ProviderUserFetcher {

    private final AppleTokenVerifier verifier;

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String authData) {
        Claims claims = verifier.verify(authData);

        String id = claims.getSubject();
        String email = (String) claims.get("email");
        boolean emailVerified = Boolean.getBoolean((String) claims.get("email_verified"));

        return SocialProvider.builder()
                .providerId(id)
                .providerType(providerType)
                .email(emailVerified ? email : null)
                .build();
    }
}
