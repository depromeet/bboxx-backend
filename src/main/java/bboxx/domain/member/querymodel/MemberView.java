package bboxx.domain.member.querymodel;

import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProviderType;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberView {

    private Long id;
    private String nickname;
    private MemberState state;
    private SocialProviderType socialProviderType;

    public static MemberView of(Member member) {
        return MemberView.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .state(member.getState())
                .socialProviderType(member.getProviderType())
                .build();
    }
}
