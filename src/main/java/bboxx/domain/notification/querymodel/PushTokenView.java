package bboxx.domain.notification.querymodel;

import bboxx.domain.member.Member;
import bboxx.domain.member.MemberState;
import bboxx.domain.member.SocialProviderType;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.PushTokenState;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PushTokenView {

    private Long id;
    private Long ownerId;
    private PushTokenState state;

    public static PushTokenView of(PushToken pushToken) {
        return PushTokenView.builder()
                .id(pushToken.getId())
                .ownerId(pushToken.getOwnerId())
                .state(pushToken.getState())
                .build();
    }
}