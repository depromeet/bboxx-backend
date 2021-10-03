package bboxx.infrastructure.jwt;

import bboxx.infrastructure.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtProvider")
public class JwtProviderTest {

    @Test
    public void JWT가_생성되었다면_입력된_id_nickname_값을_가지게_된다() {
        // given
        String secret = "ltDuCltDuCHi32136qoIV4Y1rElto1KYoEyBpvuRHi36qoIV4Y1rElto1KYoEyBpvuR";
        String memberId = "12";
        String nickname = "bboxxibal";
        JwtProvider jwtProvider = new JwtProvider(secret);

        // when
        String token = jwtProvider.generateToken(memberId, nickname);

        // then
        Claims claims = jwtProvider.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo(memberId);
        assertThat(claims.get("nickname")).isEqualTo(nickname);

    }
}
