package bboxx.infrastructure.jwt;

import bboxx.domain.member.commandmodel.TokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider implements TokenGenerator {

    @Value("${bboxx.jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 7 * 24 * 3600 * 1000; // 7 days

    public String generateToken(String id, String nickname) {
        Date now = new Date(System.currentTimeMillis());

        Date expiredDate = new Date(now.getTime() + EXPIRATION_TIME);


        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("nickname", nickname);

        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void verifyToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}