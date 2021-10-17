package bboxx.domain.notification;

import bboxx.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class PushToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long ownerId;

    @Column
    private String token;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PushTokenState state;

    public PushToken(Long id, Long ownerId, String token, PushTokenState state) {
        this.id = id;
        this.ownerId = ownerId;
        this.token = token;
        this.state = state;
    }

    public PushToken(Long ownerId, String token, PushTokenState state) {
        this.ownerId = ownerId;
        this.token = token;
        this.state = state;
    }

    public void registerToken(String token) {
        this.state = PushTokenState.ENABLED;
        this.token = token;
    }

    public void changeToken(String token) {
        this.token = token;
    }
}
