package bboxx.application.security;

import bboxx.infrastructure.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Pattern PATTERN_AUTHORIZATION_HEADER = Pattern.compile("[Bb]earer (.+)");

    private final JwtProvider jwtProvider;
    private final AuthUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = this.extractToken(httpServletRequest);
            this.jwtProvider.verifyToken(token);

            Long memberId = Long.parseLong(this.jwtProvider.getClaims(token).getSubject());
            UserDetails userDetails = new AuthUserDetail(memberId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            // security context 에 지정되는 값이 없어진다. -> log 만 찍으면 좋을듯?
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        final Matcher matcher = PATTERN_AUTHORIZATION_HEADER.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid authorization header. header:" + authorizationHeader);
        }
        return matcher.group(1);
    }
}
