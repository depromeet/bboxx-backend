package bboxx.domain.member;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.commandmodel.MemberCreator;
import bboxx.domain.member.commandmodel.MemberRepository;
import org.h2.api.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("MemberCreatorTest")
@ExtendWith(MockitoExtension.class)
public class MemberCreatorTest {

    @Test
    public void 유저가_존재하지_않는다면_유저_생성_후_회원정보를_반환한다() {
        // given
        MemberRepository memberRepository = new FakeMemberRepository();
        MemberCreator memberCreator = new MemberCreator(memberRepository);
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");

        // when
        Member result = memberCreator.execute(provider, "nickname");

        // then
        assertThat(result.getProviderId()).isEqualTo((provider.getProviderId()));
        assertThat(result.getState()).isEqualTo((MemberState.ACTIVE));
    }

    @Test
    public void 유저가_존재한다면_MEMBER_EXISTED_에러를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        MemberCreator memberCreator = new MemberCreator(memberRepository);
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        Member existedMember = new Member(12L, "nicknames", MemberState.ACTIVE, provider);
        memberRepository.members.add(existedMember);

        // when
        // then
        DomainException result = assertThrows(DomainException.class, () -> {
            memberCreator.execute(provider, "nickname");
        });
        assertThat(result).isEqualTo(new DomainException(DomainErrorCode.MEMBER_EXISTED_ERROR));
    }
}
