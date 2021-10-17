package bboxx.domain.notification.querymodel;

import bboxx.domain.notification.PushToken;

import java.util.Optional;

public interface PushTokenReader {
    Optional<PushToken> findByOwnerId(Long id);
}
