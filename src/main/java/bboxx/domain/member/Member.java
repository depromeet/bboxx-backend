package bboxx.domain.member;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MemberState state;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private SocialProvider provider;

    public Member(Long id, String nickname, MemberState state, SocialProvider provider) {
        this.id = id;
        this.nickname = nickname;
        this.provider = provider;
        this.state = state;
        this.provider.setMember(this);
    }

    public Member(String nickname, MemberState state, SocialProvider provider) {
        this.nickname = nickname;
        this.provider = provider;
        this.state = state;
        this.provider.setMember(this);
    }

    public Member(MemberState state, SocialProvider provider) {
        this.provider = provider;
        this.state = state;
        this.provider.setMember(this);
    }

    public Member(Long id, String nickname, MemberState state) {
        this.id = id;
        this.nickname = nickname;
        this.state = state;
    }

    public String getProviderId() {
        return this.provider.getProviderId();
    }

    public SocialProviderType getProviderType() {
        return this.provider.getProviderType();
    }

    public void updateInfo(String nickname) {
        Optional.of(nickname).ifPresent(this::changeNickname);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
