package bboxx.domain.member;

import bboxx.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String ageRange;

    @Column
    private String profileImage;


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

    public SocialProvider(SocialProviderType providerType, String providerId, Member member) {
        this.providerType = providerType;
        this.providerId = providerId;
        this.member = member;
    }
}
