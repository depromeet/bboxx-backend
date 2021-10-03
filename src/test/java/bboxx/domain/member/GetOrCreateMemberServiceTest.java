package bboxx.domain.member;

import bboxx.domain.member.commandmodel.GetOrCreateMemberService;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.member.commandmodel.NicknameGenerator;
import bboxx.infrastructure.nicknamegenerator.RandomNicknameGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GetOrCreateMemberService")
@ExtendWith(MockitoExtension.class)
public class GetOrCreateMemberServiceTest {

    @Test
    public void 유저가_존재하지_않는다면_유저_생성_후_회원정보를_반환한다() {
        // given
        MemberRepository memberRepository = new FakeMemberRepository();
        NicknameGenerator nicknameGenerator = new RandomNicknameGenerator();
        GetOrCreateMemberService getOrCreateMemberService = new GetOrCreateMemberService(memberRepository, nicknameGenerator);
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");

        // when
        Member result = getOrCreateMemberService.execute(provider);

        // then
        assertThat(memberRepository.findById(result.getId())).isPresent();
        assertThat(result.getProviderId()).isEqualTo((provider.getProviderId()));
    }

    @Test
    public void 유저가_존재한다면_존재하는_회원정보를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        NicknameGenerator nicknameGenerator = new RandomNicknameGenerator();
        GetOrCreateMemberService getOrCreateMemberService = new GetOrCreateMemberService(memberRepository, nicknameGenerator);
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");

        Member existedMember = new Member(nicknameGenerator.generate(), provider);
        memberRepository.members.add(existedMember);

        // when
        Member result = getOrCreateMemberService.execute(provider);

        // then
        assertThat(result.getProviderId()).isEqualTo((existedMember.getProviderId()));
        assertThat(result.getId()).isEqualTo((existedMember.getId()));
    }
}

