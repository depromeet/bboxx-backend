package bboxx.application.security;

import bboxx.infrastructure.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthExceptionEntryPoint authenticationExceptionEntryPoint;
    private final AuthUserDetailService authUserDetailService;
    private final JwtProvider jwtProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return rawPassword.toString();
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return true;
                    }
                });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtProvider, authUserDetailService);

        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**","/swagger/**", "/health").permitAll()
                .antMatchers("/h2-console/**").permitAll() // h2 console
                .antMatchers("/api/v1/auth/signin", "/api/v1/auth/signup", "/api/v1/auth/validate-jwt/**").permitAll()
                .antMatchers("/api/v1/generate-member-nickname").permitAll()
//                .antMatchers("/api/auth/validate-jwt/**").hasAnyRole("USER") // -> USER_ROLE 설정됨
//                .anyRequest().permitAll().and() // 임시로 모든 요청을 허용
                .anyRequest().authenticated().and() // 해당 요청을 인증된 사용자만 사용 가능
                .headers()
                    .frameOptions()
                    .sameOrigin().and()
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationExceptionEntryPoint).and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        ;
    }
}
