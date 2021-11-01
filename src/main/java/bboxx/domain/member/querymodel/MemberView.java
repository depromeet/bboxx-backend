package bboxx.domain.member.querymodel;

import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProviderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberView {

    @ApiModelProperty(value = "사용자 고유 id", example = "1")
    private Long id;
    @ApiModelProperty(value = "사용자 닉네임", example = "사랑스러운딸기")
    private String nickname;
    @ApiModelProperty(value = "사용자 상태 (생성 : CREATED, 활성화 : ACTIVE)", example = "ACTIVE")
    private MemberState state;
    @ApiModelProperty(value = "소셜 로그인 Provider(KAKAO, APPLE, GOOGLE)", example = "KAKAO")
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
