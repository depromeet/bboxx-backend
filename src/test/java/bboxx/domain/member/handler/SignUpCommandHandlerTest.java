package bboxx.domain.member.handler;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.*;
import bboxx.domain.member.command.SignUpCommand;
import bboxx.domain.member.command.SignUpCommandResult;
import bboxx.domain.member.commandmodel.ProviderUserFetcher;
import bboxx.domain.member.commandmodel.TokenGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SignUpCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class SignUpCommandHandlerTest {

    @Test
    public void 유저가_존재한다면_MEMBER_EXISTED_에러를_반환한다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        ProviderUserFetcher providerUserFetcher = (providerType, authData) -> provider;
        TokenGenerator tokenGenerator = (id, nickname) -> "token1234556677";

        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member existedMember = new Member(111L, "nickname", MemberState.ACTIVE, provider);
        memberRepository.members.add(existedMember);
        SignUpCommand command = new SignUpCommand(provider.getProviderType(), "authData", "nicknames");

        SignUpCommandHandler sut = new SignUpCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);

        // when
        // then
        DomainException result = assertThrows(DomainException.class, () -> {
            sut.handle(command);
        });
        assertThat(result).isEqualTo(new DomainException(DomainErrorCode.MEMBER_EXISTED_ERROR));
    }

    @Test
    public void 유저가_존재하지_않는다면_SignUpCommandResult_에_토큰_이_반환된다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        ProviderUserFetcher providerUserFetcher = (providerType, authData) -> provider;
        TokenGenerator tokenGenerator = (id, nickname) -> "token1234556677";

        FakeMemberRepository memberRepository = new FakeMemberRepository();
        SignUpCommand command = new SignUpCommand(provider.getProviderType(), "authData", "nicknames");

        SignUpCommandHandler sut = new SignUpCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);

        // when
        SignUpCommandResult actual = sut.handle(command);

        // then
        assertThat(actual.getToken()).isNotNull();
    }

    @Test
    public void 회원가입에_성공하면_새로운_유저가_생성된다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        ProviderUserFetcher providerUserFetcher = (providerType, authData) -> provider;
        TokenGenerator tokenGenerator = (id, nickname) -> "token1234556677";

        FakeMemberRepository memberRepository = new FakeMemberRepository();
        String nickname = "닉네임이다아";
        SignUpCommand command = new SignUpCommand(provider.getProviderType(), "authData", nickname);

        SignUpCommandHandler sut = new SignUpCommandHandler(providerUserFetcher, memberRepository, tokenGenerator);

        // when
        sut.handle(command);
        Member actual = memberRepository.findByProviderIdAndProviderType(provider.getProviderId(), provider.getProviderType()).get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNickname()).isEqualTo(nickname);
        assertThat(actual.getProviderId()).isEqualTo((provider.getProviderId()));
        assertThat(actual.getState()).isEqualTo((MemberState.ACTIVE));
    }
}