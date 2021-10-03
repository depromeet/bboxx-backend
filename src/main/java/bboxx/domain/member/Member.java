package bboxx.domain.member;

import bboxx.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "member")
    private SocialProvider provider;

    public Member(String nickname, SocialProvider provider) {
        this.nickname = nickname;
        this.provider = provider;
        this.provider.setMember(this);
    }

    public String getProviderId() {
        return this.provider.getProviderId();
    }

    public SocialProviderType getProviderType() {
        return this.provider.getProviderType();
    }
}
