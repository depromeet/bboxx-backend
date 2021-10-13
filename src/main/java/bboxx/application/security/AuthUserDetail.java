package bboxx.application.security;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class AuthUserDetail implements UserDetails {

    private final Long id;
    private final Set<GrantedAuthority> authorities;

    public AuthUserDetail(Long id) {
        this.id = id;
        this.authorities = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "password"; // password 방식 사용하지 않음
    }

    @Override
    public String getUsername() {
        return Long.toString(this.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void validateSameUser(Long id) {
        if (!id.equals(this.id)) {
            throw new DomainException(DomainErrorCode.UNAUTHORIZED_ERROR);
        }
    }
}
