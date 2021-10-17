package bboxx.domain.notification.commandmodel;

import bboxx.domain.notification.PushToken;

import java.util.Optional;

public interface PushTokenRepository {
    PushToken save(PushToken pushToken);
    Optional<PushToken> findById(Long id);
    Optional<PushToken> findByOwnerId(Long ownerId);
}
