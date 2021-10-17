package bboxx.infrastructure.repository;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import bboxx.domain.notification.querymodel.PushTokenReader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPushTokenRepository extends PushTokenRepository, PushTokenReader, JpaRepository<PushToken, Long> {

}
