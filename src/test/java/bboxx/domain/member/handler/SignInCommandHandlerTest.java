package bboxx.domain.member.handler;

import bboxx.domain.member.*;
import bboxx.domain.member.command.SignInCommand;
import bboxx.domain.member.command.SignInCommandResult;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SignInCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class SignInCommandHandlerTest {

    @Test
    public void 유저가_존재한다면_SignInCommandResult_에_토큰_이_반환된다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        ProviderUserFetcher providerUserFetcher = (providerType, authData) -> provider;
        TokenGenerator tokenGenerator = (id, nickname) -> "token1234556677";

        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member existedMember = new Member(111L, "nickname", MemberState.ACTIVE, provider);
        memberRepository.members.add(existedMember);
        SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

        SignInCommandHandler sut = new SignInCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);

        // when
        SignInCommandResult actual = sut.handle(command);

        // then
        assertThat(actual.getToken()).isNotNull();
    }

    @Test
    public void 유저가_존재하지_않는다면_SignInCommandResult_가_비어서_반환된다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        ProviderUserFetcher providerUserFetcher = (providerType, authData) -> provider;
        TokenGenerator tokenGenerator = (id, nickname) -> "token1234556677";

        FakeMemberRepository memberRepository = new FakeMemberRepository();
        SignInCommand command = new SignInCommand(provider.getProviderType(), "authData");

        SignInCommandHandler sut = new SignInCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);

        // when
        SignInCommandResult actual = sut.handle(command);

        // then
        assertThat(actual.getToken()).isNull();
    }
}