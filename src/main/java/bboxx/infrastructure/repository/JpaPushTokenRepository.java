package bboxx.infrastructure.repository;

import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.commandmodel.PushTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPushTokenRepository extends PushTokenRepository, JpaRepository<PushToken, Long> {

}
