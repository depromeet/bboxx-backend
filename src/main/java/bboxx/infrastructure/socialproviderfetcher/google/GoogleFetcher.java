package bboxx.infrastructure.socialproviderfetcher.google;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RequiredArgsConstructor
@Component(value = "google_fetcher")
public class GoogleFetcher implements ProviderUserFetcher {

    @Value("${bboxx.google.client-id}")
    private String clientId;

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String authData) {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(authData);
        } catch (GeneralSecurityException e) {
            throw new DomainException(DomainErrorCode.UNAUTHORIZED_ERROR);
        } catch (IOException e) {
            throw new DomainException(DomainErrorCode.INTERNAL_SERVER_ERROR);
        }

        if (idToken == null) {
            System.out.println("invalid id token");
            throw new DomainException(DomainErrorCode.UNAUTHORIZED_ERROR);
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        return SocialProvider.builder()
                .providerId(userId)
                .providerType(providerType)
                .email(emailVerified ? email : null)
                .profileImage(pictureUrl)
                .nickname(name)
                .build();
    }
}
