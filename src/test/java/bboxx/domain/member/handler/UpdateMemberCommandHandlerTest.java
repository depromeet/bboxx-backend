package bboxx.domain.member.handler;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.*;
import bboxx.domain.member.command.UpdateMemberCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("UpdateMemberCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
public class UpdateMemberCommandHandlerTest {
    @Test
    public void 유저가_존재한지_않는다면_MEMBER_NOT_FOUND_ERROR_에러를_반환한다() {
        // given
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        UpdateMemberCommand command = new UpdateMemberCommand(1L, "닉네임닉네임");

        UpdateMemberCommandHandler sut = new UpdateMemberCommandHandler(memberRepository);

        // when
        // then
        DomainException actual = assertThrows(DomainException.class, () -> {
            sut.handle(command);
        });
        assertThat(actual).isEqualTo(new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));
    }

    @Test
    public void 업데이트에_성공하면_정보가_변경된다() {
        // given
        SocialProvider provider = new SocialProvider(SocialProviderType.KAKAO, "12343335");
        Long memberId = 111L;
        FakeMemberRepository memberRepository = new FakeMemberRepository();
        Member existedMember = new Member(memberId, "nickname", MemberState.ACTIVE, provider);
        memberRepository.save(existedMember);

        UpdateMemberCommand command = new UpdateMemberCommand(memberId, "닉네임닉네임");
        UpdateMemberCommandHandler sut = new UpdateMemberCommandHandler(memberRepository);

        // when
        sut.handle(command);

        // then
        Member actual = memberRepository.findById(memberId).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getNickname()).isEqualTo(command.getNickname());
    }
}
