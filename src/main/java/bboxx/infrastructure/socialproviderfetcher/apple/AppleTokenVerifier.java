package bboxx.infrastructure.socialproviderfetcher.apple;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class AppleTokenVerifier {

    @Value("${bboxx.apple.client-id}")
    String clientId;

    private final AppleUserClient appleUserClient;
    private final ObjectMapper objectMapper;

    public Claims verify(String idToken) {
        String headerIdToken = idToken.split("\\.")[0];
        try {
            Map<String, String> header = objectMapper.readValue(new String(Base64.getDecoder().decode(headerIdToken), StandardCharsets.UTF_8), new TypeReference<>() {
            });
            PublicKey publicKey = getPublicKey(header);
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .requireIssuer("https://appleid.apple.com")
                    .requireAudience(clientId)
                    .build()
                    .parseClaimsJws(idToken)
                    .getBody();
        } catch (JsonProcessingException | InvalidKeySpecException | InvalidClaimException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(DomainErrorCode.INTERNAL_SERVER_ERROR);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(DomainErrorCode.UNAUTHORIZED_ERROR);
        }
    }

    private PublicKey getPublicKey(Map<String, String> header) throws InvalidKeySpecException, NoSuchAlgorithmException {
        ApplePublicKeyResponse response = appleUserClient.getAppleAuthPublicKey();
        ApplePublicKeyResponse.Key key = response.getMatchedPublicKey(header.get("kid"), header.get("alg"));

        byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
        return keyFactory.generatePublic(publicKeySpec);
    }

}