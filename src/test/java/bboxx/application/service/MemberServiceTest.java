package bboxx.application.service;

import bboxx.application.service.member.MemberService;
import bboxx.application.service.member.command.SignInCommand;
import bboxx.application.service.member.command.SignInCommandResult;
import bboxx.domain.member.*;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.infrastructure.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("MemberService")
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private ProviderUserFetcher providerUserFetcher;

    @Nested
    class signIn {
        @Test
        public void 유저가_존재한다면_SignInCommandResult_에_jwt_가_반환된다() {
            // given
            SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
            given(providerUserFetcher.fetch(any(), any()))
                    .willReturn(provider);

            FakeMemberRepository memberRepository = new FakeMemberRepository();
            MemberService memberService = new MemberService(providerUserFetcher, memberRepository, new JwtProvider("ltDuCltDuCHi32136qoIV4Y1rElto1KYoEyBpvuRHi36qoIV4Y1rElto1KYoEyBpvuR"));


            Member existedMember = new Member(111L, "nickname", MemberState.ACTIVE, provider);
            memberRepository.members.add(existedMember);

            SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

            // when
            SignInCommandResult result = memberService.signIn(command);

            // then
            assertThat(result.getJwt()).isNotNull();
        }

        @Test
        public void 유저가_존재하지_않는다면_SignInCommandResult_가_비어서_반환된다() {
            // given
            SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
            given(providerUserFetcher.fetch(any(), any()))
                    .willReturn(provider);

            FakeMemberRepository memberRepository = new FakeMemberRepository();
            MemberService memberService = new MemberService(providerUserFetcher, memberRepository, new JwtProvider("ltDuCltDuCHi32136qoIV4Y1rElto1KYoEyBpvuRHi36qoIV4Y1rElto1KYoEyBpvuR"));

            SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

            // when
            SignInCommandResult result = memberService.signIn(command);

            // then
            assertThat(result.getJwt()).isNull();
        }
    }
}