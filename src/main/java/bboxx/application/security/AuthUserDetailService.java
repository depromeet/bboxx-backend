package bboxx.application.security;

import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public AuthUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = this.memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("unauthorized error"));

        // user 세팅
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new AuthUserDetail(member.getId(), authorities);
    }
}