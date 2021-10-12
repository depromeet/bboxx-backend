package bboxx.application.security;

import bboxx.domain.exception.DomainException;
import bboxx.domain.member.FakeMemberRepository;
import bboxx.domain.member.Member;
import bboxx.domain.member.SocialProvider;
import bboxx.domain.member.SocialProviderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("AuthUserDetailService")
@ExtendWith(MockitoExtension.class)
public class AuthUserDetailServiceTest {

    @Test
    public void 유저가_존재한다면_USERDETAILS_를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        AuthUserDetailService authUserDetailService = new AuthUserDetailService(memberRepository);

        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");

        long id = 12;
        Member existedMember = new Member(id, "nickname", provider);
        memberRepository.members.add(existedMember);

        // when
        UserDetails result = authUserDetailService.loadUserByUsername(Long.toString(id));

        // then
        assertThat(result.getUsername()).isEqualTo(Long.toString(existedMember.getId()));
    }

    @Test
    public void Id_가_Long_이_아니라면_에러를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        AuthUserDetailService authUserDetailService = new AuthUserDetailService(memberRepository);
        String id = "123.1212";

        // when
        // then
        Exception result = assertThrows(UsernameNotFoundException.class, () -> {
            authUserDetailService.loadUserByUsername(id);
        });
        assertThat(result.getMessage()).isEqualTo("id type must be long, id: " + id);
    }

    @Test
    public void Member_가_존재하지_않는다면_에러를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        AuthUserDetailService authUserDetailService = new AuthUserDetailService(memberRepository);
        String id = "123";

        // when
        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            authUserDetailService.loadUserByUsername("unauthorized error, unexpected id: " + id);
        });
    }
}