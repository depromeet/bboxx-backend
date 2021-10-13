package bboxx.application.service;

import bboxx.application.service.member.MemberCommandService;
import bboxx.domain.member.command.SignInCommand;
import bboxx.domain.member.command.SignInCommandResult;
import bboxx.domain.member.*;
import bboxx.domain.member.commandmodel.MemberCreator;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;
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
public class MemberCommandServiceTest {

    @Mock
    private ProviderUserFetcher providerUserFetcher;

    @Mock
    private TokenGenerator tokenGenerator;

    @Nested
    class signIn {
        @Test
        public void 유저가_존재한다면_SignInCommandResult_에_토큰_이_반환된다() {
            // given
            SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
            given(providerUserFetcher.fetch(any(), any()))
                    .willReturn(provider);
            given(tokenGenerator.generateToken(any(), any()))
                    .willReturn("token1234556677");

            FakeMemberRepository memberRepository = new FakeMemberRepository();
            MemberCommandService memberCommandService = new MemberCommandService(providerUserFetcher, memberRepository, new MemberCreator(memberRepository), tokenGenerator);


            Member existedMember = new Member(111L, "nickname", MemberState.ACTIVE, provider);
            memberRepository.members.add(existedMember);

            SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

            // when
            SignInCommandResult result = memberCommandService.signIn(command);

            // then
            assertThat(result.getToken()).isNotNull();
        }

        @Test
        public void 유저가_존재하지_않는다면_SignInCommandResult_가_비어서_반환된다() {
            // given
            SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
            given(providerUserFetcher.fetch(any(), any()))
                    .willReturn(provider);

            FakeMemberRepository memberRepository = new FakeMemberRepository();
            MemberCommandService memberCommandService = new MemberCommandService(providerUserFetcher, memberRepository, new MemberCreator(memberRepository), tokenGenerator);

            SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

            // when
            SignInCommandResult result = memberCommandService.signIn(command);

            // then
            assertThat(result.getToken()).isNull();
        }
    }
}