package bboxx.application.security;

import bboxx.domain.member.commandmodel.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private Environment environment;

    private final MemberRepository memberRepository;

    public AuthUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if (!id.matches("[0-9]+")) {
            throw new UsernameNotFoundException("id type must be long, id: " + id);
        }
        Long memberId = Long.parseLong(id);
        if (!this.isSkip()) {
            memberRepository.findById(memberId)
                    .orElseThrow(() -> new UsernameNotFoundException("unauthorized error, unexpected id: " + id));
        }

        // user 세팅
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new AuthUserDetail(memberId, authorities);
    }

    private boolean isSkip() {
        boolean skip = false;
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equals("local") || profile.equals("dev")) {
                skip = true;
                break;
            }
        }
        return skip;
    }
}