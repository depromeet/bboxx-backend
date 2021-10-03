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
public class SocialProvider extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SocialProviderType providerType;

    @Column
    private String providerId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected void setMember(Member member) {
        this.member = member;
    }

    public SocialProvider(SocialProviderType socialProviderType, String providerId) {
        this.providerType = socialProviderType;
        this.providerId = providerId;
    }
}
